package com.ch.android.resource.controller;

import android.content.Context;

import com.ch.android.resource.Configuration;
import com.ch.android.resource.InternalResourceManager;
import com.ch.android.resource.controller.data_structure.HomeData;
import com.ch.android.resource.controller.data_structure.ResourcePackage;
import com.ch.android.resource.model.AssertHelper;
import com.ch.android.resource.model.DiskHelper;
import com.ch.android.resource.model.LoadCallback;
import com.ch.android.resource.model.NetHelper;
import com.ch.android.resource.util.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ch.android.resource.Configuration.GENERAL_DATA_URI;


public class GeneralDataHelper {

    private static final String TAG = "GeneralDataHelper";

    private Context mContext;
    private InternalResourceManager mInternalResMgr;

    /**
     * 当前正在使用的{@link ResourcePackage}，由{@link #mDefaultResPkg}和{@link #mExtraResPkgList}
     * 共同计算而来。
     */
    private ResourcePackage mCurrentResPkg;

    /**
     * 默认使用的{@link ResourcePackage}。
     */
    private ResourcePackage mDefaultResPkg;
    /**
     * 特殊情况下使用的{@link ResourcePackage}列表。
     */
    private List<ResourcePackage> mExtraResPkgList;

    public GeneralDataHelper(InternalResourceManager internalResMgr) {
        mContext = internalResMgr.getContext();
        mInternalResMgr = internalResMgr;
        mExtraResPkgList = new ArrayList<>();

        File rootDir = new File(Configuration.getCacheRootDirectoryPath(mContext));

        createCacheRootDirIfNotExisted(rootDir);

        if (isCacheRootDirEmpty(rootDir)) {
            // 如果缓存根目录没有任何文件，那么通过asserts中的内置文件来初始化缓存文件目录结构。
            String jsonStr = AssertHelper.getDefault(mContext).parseJson(
                    Configuration.BUILD_IN_GENERAL_DATA_FILE_NAME);
            jsonStr = "";// TODO 调试用
            if (jsonStr != null) {
                List<ResourcePackage> defResPkgOut = new ArrayList<>();
                parseJsonToResourcePackages(null, defResPkgOut, mExtraResPkgList);
                if (defResPkgOut.size() > 0) {
                    mDefaultResPkg = defResPkgOut.get(0);
                }

                updateResourcePackages(mDefaultResPkg, mExtraResPkgList);
            } else {
                throw new NullPointerException("Build-in general data file is not exists.");
            }
        }


        // TODO 需要删除
        List<ResourcePackage> defResPkgOut = new ArrayList<>();
        parseJsonToResourcePackages(null, defResPkgOut, mExtraResPkgList);
        if (defResPkgOut.size() > 0) {
            mDefaultResPkg = defResPkgOut.get(0);
        }

        // 构建缓存目录结构。
        List<ResourcePackage> resPkgList = new ArrayList<>();
        resPkgList.add(mDefaultResPkg);
        resPkgList.addAll(mExtraResPkgList);
        buildCacheDirectoryStructure(resPkgList);

        // 从磁盘中解析出数据。
        initAllResourcePackageFromDisk();

        // 通过mDefaultResPkg和mExtraResPkgList计算出mCurrentResPkg。
        computeCurrentResourcePackage();

        // 通知更新缓存Bitmap。
        notifyToCacheBitmap();
    }

    public HomeData.ItemInfoListBean getDataForModule(int module) {
        return mCurrentResPkg.getDataForModule(HomeData.ItemInfoListBean.getStringType(module));
    }

    public void refresh() {
        // 1.从网络中获取最新数据。
        NetHelper.getDefault(mContext).loadJsonString(GENERAL_DATA_URI,
                new LoadCallback<String>() {
                    @Override
                    public void onLoadCompleted(String result) {
                        // 2.解析成ResourcePackage对象。
                        ResourcePackage defResPkg = null;
                        List<ResourcePackage> extraResPkgList = new ArrayList<>();
                        List<ResourcePackage> defResPkgOut = new ArrayList<>();
                        parseJsonToResourcePackages(null, defResPkgOut, mExtraResPkgList);
                        if (defResPkgOut.size() > 0) {
                            defResPkg = defResPkgOut.get(0);
                        }

                        // 3.与现有ResourcePackage对象合并。
                        updateResourcePackages(defResPkg, extraResPkgList);

                        // 4.重新计算
                        computeCurrentResourcePackage();

                        // 5.更新磁盘内容
                        refreshDiskData();

                        // 6.通知出去，比如BitmapResourceHelper需要更新Bitmap缓存
                        notifyToCacheBitmap();
                    }

                    @Override
                    public void onLoadFailed(Throwable error) {

                    }
                });
    }

    private void createCacheRootDirIfNotExisted(File rootDir) {
        if (!rootDir.exists()) {
            boolean result = rootDir.mkdir();
            LogUtils.d(TAG, "createCacheRootDirIfNotExisted >> result=" + result);
        }
    }

    /**
     * 通过{@link #mDefaultResPkg}和{@link #mExtraResPkgList}来更新磁盘缓存文件
     */
    private void refreshDiskData() {

        File rootCacheDir = new File(Configuration.getCacheRootDirectoryPath(mContext));
        String[] resPkgDirNames = rootCacheDir.list();
        if (resPkgDirNames == null) {
            resPkgDirNames = new String[0];
        }

        List<ResourcePackage> allResPkgList = new ArrayList<>();
        allResPkgList.add(mDefaultResPkg);
        allResPkgList.addAll(mExtraResPkgList);

        // 1.新增ResourcePackage
        // 2.更新已有ResourcePackage
        List<ResourcePackage> toAdd = new ArrayList<>();
        List<ResourcePackage> toUpdate = new ArrayList<>();
        for (ResourcePackage resPkg : allResPkgList) {
            String resPkgName = resPkg.getType();
            boolean exists = false;
            for (String resPkgDirName : resPkgDirNames) {
                if (resPkgDirName.equals(resPkgName)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                // 更新
                toUpdate.add(resPkg);
            } else {
                // 新建
                toAdd.add(resPkg);
            }
        }

        // 3.不需要的ResourcePackage
        List<String> toDelete = new ArrayList<>();
        for (String resPkgDirName : resPkgDirNames) {
            boolean exists = false;
            for (ResourcePackage resPkg : allResPkgList) {
                if (resPkgDirName.equals(resPkg.getType())) {
                    exists = true;
                }
            }

            if (!exists) {
                toDelete.add(resPkgDirName);
            }
        }

        // 对于新增
        buildCacheDirectoryStructure(toAdd);

        // 对于更新
        for (ResourcePackage resPkg : toUpdate) {
            cacheResourcePackage(mContext, resPkg);
        }

        // 对于删除
        for (String resPkgDirName : toDelete) {
            File file = new File(rootCacheDir.getAbsolutePath() + File.separator + resPkgDirName);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 通过新的默认类型和活动类型列表来更新{@link #mDefaultResPkg}和{@link #mExtraResPkgList}
     */
    private void updateResourcePackages(ResourcePackage newDefaultPackage,
                                        List<ResourcePackage> newExtraDefaultPackage) {
        // TODO
    }

    private void notifyToCacheBitmap() {
        // 通知出去，让其他模块知道ResourcePackage发生了变化
        mInternalResMgr.updateBitmapCache(mDefaultResPkg);
        for (ResourcePackage resPkg : mExtraResPkgList) {
            mInternalResMgr.updateBitmapCache(resPkg);
        }
    }

    private void parseJsonToResourcePackages(String jsonStr, List<ResourcePackage> defResPkgOut,
                                             List<ResourcePackage> extraResPkgListOut) {

        // TODO 模拟数据, 之后要真正从jsonStr中解析出以下这些对象

        HomeData.ItemInfoListBean moduleA = new HomeData.ItemInfoListBean();
        moduleA.itemType = "topBanner";
        moduleA.module = "topBanner";
        moduleA.itemContentList = new ArrayList<>();

        HomeData.ItemInfoListBean.ItemContentListBean aContentListBean1 = new HomeData.ItemInfoListBean.ItemContentListBean();
        aContentListBean1.imageUrl = "https://m.360buyimg.com/mobilecms/s720x322_jfs/t3097/241/9768114398/78418/47e4335e/58d8a637N6f178fbd.jpg!q70.jpg";
        aContentListBean1.clickUrl = "慕尼黑新品上市";
        moduleA.itemContentList.add(aContentListBean1);
        HomeData.ItemInfoListBean.ItemContentListBean aContentListBean2 = new HomeData.ItemInfoListBean.ItemContentListBean();
        aContentListBean2.imageUrl = "https://m.360buyimg.com/mobilecms/s720x322_jfs/t4282/364/2687292678/87315/e4311cd0/58d4d923N24a2f5eb.jpg!q70.jpg";
        aContentListBean2.clickUrl = "空调让你百试不爽";
        moduleA.itemContentList.add(aContentListBean2);
        HomeData.ItemInfoListBean.ItemContentListBean aContentListBean3 = new HomeData.ItemInfoListBean.ItemContentListBean();
        aContentListBean3.imageUrl = "https://img1.360buyimg.com/da/jfs/t4162/171/1874609280/92523/a1206b3f/58c7a832Nc8582e81.jpg";
        aContentListBean3.clickUrl = "奥妙全新上市";
        moduleA.itemContentList.add(aContentListBean3);

        HomeData.ItemInfoListBean moduleB = new HomeData.ItemInfoListBean();
        moduleB.itemType = "iconList";
        moduleB.module = "iconList";
        moduleB.itemContentList = new ArrayList<>();

        HomeData.ItemInfoListBean.ItemContentListBean bContentListBean1 = new HomeData.ItemInfoListBean.ItemContentListBean();
        bContentListBean1.imageUrl = "https://upload-images.jianshu.io/upload_images/2634235-e5a341b90aba9f9d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
        bContentListBean1.itemTitle = "淘宝";
        moduleB.itemContentList.add(bContentListBean1);
        HomeData.ItemInfoListBean.ItemContentListBean bContentListBean2 = new HomeData.ItemInfoListBean.ItemContentListBean();
        bContentListBean2.imageUrl = "https://upload-images.jianshu.io/upload_images/2634235-ad1af8c648763cff.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
        bContentListBean2.itemTitle = "京东";
        moduleB.itemContentList.add(bContentListBean2);
        HomeData.ItemInfoListBean.ItemContentListBean bContentListBean3 = new HomeData.ItemInfoListBean.ItemContentListBean();
        bContentListBean3.imageUrl = "https://upload-images.jianshu.io/upload_images/2634235-8be85daea6170470.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
        bContentListBean3.itemTitle = "蘑菇街";
        moduleB.itemContentList.add(bContentListBean3);
        HomeData.ItemInfoListBean.ItemContentListBean bContentListBean4 = new HomeData.ItemInfoListBean.ItemContentListBean();
        bContentListBean4.imageUrl = "https://upload-images.jianshu.io/upload_images/2634235-58f1853fef378579.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
        bContentListBean4.itemTitle = "唯品会";
        moduleB.itemContentList.add(bContentListBean4);
        HomeData.ItemInfoListBean.ItemContentListBean bContentListBean5 = new HomeData.ItemInfoListBean.ItemContentListBean();
        bContentListBean5.imageUrl = "https://upload-images.jianshu.io/upload_images/2634235-c40e1aa68113b09d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
        bContentListBean5.itemTitle = "红人装";
        moduleB.itemContentList.add(bContentListBean5);

        HomeData.ItemInfoListBean moduleC = new HomeData.ItemInfoListBean();
        moduleC.itemType = "newUser";
        moduleC.module = "newUser";
        moduleC.itemContentList = new ArrayList<>();

        HomeData.ItemInfoListBean.ItemContentListBean cContentListBean1 = new HomeData.ItemInfoListBean.ItemContentListBean();
        cContentListBean1.imageUrl = "https://img10.360buyimg.com/mobilecms/s500x500_jfs/t8638/5/1380338857/137855/1c1f5171/59b8d8bcNc3d95d6e.jpg";
        cContentListBean1.clickUrl = "新用户专享";
        cContentListBean1.itemTitle = "京东超市";
        moduleC.itemContentList.add(cContentListBean1);
        HomeData.ItemInfoListBean.ItemContentListBean cContentListBean2 = new HomeData.ItemInfoListBean.ItemContentListBean();
        cContentListBean2.imageUrl = "https://img13.360buyimg.com/mobilecms/s500x500_jfs/t3448/27/1001281942/96818/dda55698/581af181Nee668ef2.jpg";
        cContentListBean2.clickUrl = "限时秒杀";
        cContentListBean2.itemTitle = "限时秒杀";
        moduleC.itemContentList.add(cContentListBean2);
        HomeData.ItemInfoListBean.ItemContentListBean cContentListBean3 = new HomeData.ItemInfoListBean.ItemContentListBean();
        cContentListBean3.imageUrl = "https://img12.360buyimg.com/mobilecms/s500x500_jfs/t19168/182/2246056137/201636/3998f7a5/5aebd3fbNe095e52b.jpg";
        cContentListBean3.clickUrl = "热卖榜单";
        cContentListBean3.itemTitle = "热卖榜单";
        moduleC.itemContentList.add(cContentListBean3);
        HomeData.ItemInfoListBean.ItemContentListBean cContentListBean4 = new HomeData.ItemInfoListBean.ItemContentListBean();
        cContentListBean4.imageUrl = "https://img14.360buyimg.com/mobilecms/s500x500_jfs/t18250/107/355095416/223989/bb2222c7/5a6ee8faNbc10cf03.jpg!q70.jpg";
        cContentListBean4.clickUrl = "超级购物券";
        cContentListBean4.itemTitle = "超级购物券";
        moduleC.itemContentList.add(cContentListBean4);

        HomeData.ItemInfoListBean moduleD = new HomeData.ItemInfoListBean();
        moduleD.itemType = "showEvent";
        moduleD.module = "showEvent";
        moduleD.itemContentList = new ArrayList<>();

        HomeData.ItemInfoListBean.ItemContentListBean dContentListBean1 = new HomeData.ItemInfoListBean.ItemContentListBean();
        dContentListBean1.imageUrl = "https://upload-images.jianshu.io/upload_images/2634235-47edce8835868ecf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
        moduleD.itemContentList.add(dContentListBean1);



        ResourcePackage defResPkg = new ResourcePackage.Builder()
                .setType(ResourcePackage.TYPE_DEFAULT)
                .provideModule(moduleA)
                .provideModule(moduleB)
                .provideModule(moduleC)
                .provideModule(moduleD)
                .build();
        defResPkgOut.add(defResPkg);

        //------------------------------------------------------------------------------------------

        HomeData.ItemInfoListBean extraModuleC = new HomeData.ItemInfoListBean();
        extraModuleC.itemType = "newUser";
        extraModuleC.module = "newUser";
        extraModuleC.itemContentList = new ArrayList<>();

        HomeData.ItemInfoListBean.ItemContentListBean extraContentListBean1 = new HomeData.ItemInfoListBean.ItemContentListBean();
        extraContentListBean1.imageUrl = "https://img10.360buyimg.com/mobilecms/s500x500_jfs/t8638/5/1380338857/137855/1c1f5171/59b8d8bcNc3d95d6e.jpg";
        extraModuleC.itemContentList.add(cContentListBean1);
        HomeData.ItemInfoListBean.ItemContentListBean extraContentListBean2 = new HomeData.ItemInfoListBean.ItemContentListBean();
        extraContentListBean2.imageUrl = "https://img13.360buyimg.com/mobilecms/s500x500_jfs/t3448/27/1001281942/96818/dda55698/581af181Nee668ef2.jpg";
        extraModuleC.itemContentList.add(cContentListBean2);
        HomeData.ItemInfoListBean.ItemContentListBean extraContentListBean3 = new HomeData.ItemInfoListBean.ItemContentListBean();
        extraContentListBean3.imageUrl = "https://img12.360buyimg.com/mobilecms/s500x500_jfs/t19168/182/2246056137/201636/3998f7a5/5aebd3fbNe095e52b.jpg";
        extraModuleC.itemContentList.add(cContentListBean3);
        HomeData.ItemInfoListBean.ItemContentListBean extraContentListBean4 = new HomeData.ItemInfoListBean.ItemContentListBean();
        extraContentListBean4.imageUrl = "https://img14.360buyimg.com/mobilecms/s500x500_jfs/t18250/107/355095416/223989/bb2222c7/5a6ee8faNbc10cf03.jpg!q70.jpg";
        extraModuleC.itemContentList.add(cContentListBean4);

        ResourcePackage extraResPkg = new ResourcePackage.Builder()
                .setType(ResourcePackage.TYPE_EXTRA_PREFIX + "0")
                .provideModule(extraModuleC)
                .build();

        extraResPkgListOut.add(extraResPkg);
    }

    private void buildCacheDirectoryStructure(List<ResourcePackage> newResPkgList) {

        // 1.给每个ResourcePackage创建缓存目录
        for (ResourcePackage resPkg : newResPkgList) {
            createCacheDirForResourcePackage(mContext, resPkg);
        }

        // 2.在ResourcePackage对应的缓存目录中，存储ResourcePackage
        for (ResourcePackage resPkg : newResPkgList) {
            cacheResourcePackage(mContext, resPkg);
        }

        // 3.在ResourcePackage对于的缓存目录中，创建Bitmap缓存目录
        for (ResourcePackage resPkg : newResPkgList) {
            createBitmapCacheDirForResourcePackage(resPkg);
        }
    }

    private boolean isCacheRootDirEmpty(File rootDir) {
        return rootDir.list() == null || rootDir.list().length == 0;
    }

    private void createCacheDirForResourcePackage(Context context, ResourcePackage resPkg) {
        File pkgCacheDir = new File(Configuration.getCacheRootDirectoryPath(context)
                + File.separator + resPkg.getType());
        if (!pkgCacheDir.exists()) {
            boolean result = pkgCacheDir.mkdir();
            LogUtils.d(TAG, "createCacheDirForResourcePackage >> result=" + result);
        }
    }

    private void cacheResourcePackage(Context context, ResourcePackage resPkg) {
        File pkgCacheDir = new File(Configuration.getCacheRootDirectoryPath(context) +
                File.separator + resPkg.getType());
        String filePath = pkgCacheDir.getAbsolutePath() + File.separator + resPkg.getType();
        DiskHelper.getDefault(context).writeObjectWithGson(resPkg, filePath);
    }

    private void createBitmapCacheDirForResourcePackage(ResourcePackage resPkg) {
        File pkgCacheDir = new File(Configuration.getCacheRootDirectoryPath(mContext)
                + File.separator + resPkg.getType());
        if (!pkgCacheDir.exists()) {
            LogUtils.d(TAG, "createBitmapCacheDirForResourcePackage >> pkgCacheDir is not existing.");
            return;
        }

        File bitmapCacheDir = new File(pkgCacheDir.getAbsolutePath()
                + File.separator + Configuration.CACHE_BITMAP_DIR_NAME);
        if (!bitmapCacheDir.exists()) {
            bitmapCacheDir.mkdir();
        }
    }

    private void initAllResourcePackageFromDisk() {
        mExtraResPkgList.clear();

        File rootDir = new File(Configuration.getCacheRootDirectoryPath(mContext));
        String[] fileNames = rootDir.list();
        for (String fileName : fileNames) {
            String filePath = rootDir + File.separator + fileName + File.separator + fileName;
            ResourcePackage resPkg = DiskHelper.getDefault(mContext).readObjectWithGson(filePath, ResourcePackage.class);
            if (resPkg == null) {
                LogUtils.d(TAG, "initAllResourcePackageFromDisk >> Cannot readObject for " + fileName);
                continue;
            }
            if (resPkg.isDefaultResourcePackage()) {
                mDefaultResPkg = resPkg;
            } else {
                mExtraResPkgList.add(resPkg);
            }
        }
    }

    /**
     * 当{@link #mDefaultResPkg}获取{@link #mExtraResPkgList}其中之一发生变化时，一定要调用此方法重新计算
     * {@link #mCurrentResPkg}
     */
    private void computeCurrentResourcePackage() {

        // 在mExtraResPkgList需要是否有在当前时间下生效的ResourcePackage。
        long currentTimeMillis = System.currentTimeMillis();
        ResourcePackage effectiveResPkg = null;
        for (ResourcePackage resPkg : mExtraResPkgList) {
            if (currentTimeMillis < resPkg.getInvalidTimeMillis()
                    && currentTimeMillis >= resPkg.getTaskEffectTimeMillis()) {
                effectiveResPkg = resPkg;
                break;
            }
        }

        // TODO 合并规则详细
//        ModuleA moduleA = (effectiveResPkg == null || effectiveResPkg.getDataModuleA() == null)
//                ? mDefaultResPkg.getDataModuleA() : effectiveResPkg.getDataModuleA();
//        ModuleB moduleB = (effectiveResPkg == null || effectiveResPkg.getDataModuleB() == null)
//                ? mDefaultResPkg.getDataModuleB() : effectiveResPkg.getDataModuleB();
//        ModuleC moduleC = (effectiveResPkg == null || effectiveResPkg.getDataModuleC() == null)
//                ? mDefaultResPkg.getDataModuleC() : effectiveResPkg.getDataModuleC();
//        ModuleD moduleD = (effectiveResPkg == null || effectiveResPkg.getDataModuleD() == null)
//                ? mDefaultResPkg.getDataModuleD() : effectiveResPkg.getDataModuleD();
//
//        mCurrentResPkg = new ResourcePackage.Builder()
//                .setType(ResourcePackage.TYPE_CURRENT)
//                .provideDataModuleA(moduleA)
//                .provideDataModuleB(moduleB)
//                .provideDataModuleC(moduleC)
//                .provideDataModuleD(moduleD)
//                .build();

        mCurrentResPkg = mDefaultResPkg;
    }
}
