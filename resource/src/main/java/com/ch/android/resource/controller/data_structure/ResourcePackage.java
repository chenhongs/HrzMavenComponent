package com.ch.android.resource.controller.data_structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResourcePackage implements Serializable {

    private static String TAG = "ResourcePackage";

    /**
     * 当前正在使用类型。
     */
    public static final String TYPE_CURRENT = "res_pkg_current";
    /**
     * 默认类型。
     */
    public static final String TYPE_DEFAULT = "res_pkg_default";
    /**
     * 活动类型的前缀。
     */
    public static final String TYPE_EXTRA_PREFIX = "res_pkg_extra_";

    /**
     * 类型，比如默认类型、活动类型等。
     */
    private String mType = TYPE_DEFAULT;

    private long mTakeEffectTimeMillis;
    private long mInvalidTimeMillis;

    private List<HomeData.ItemInfoListBean> mModules = new ArrayList<>();

    private ResourcePackage(Builder builder) {
        mType = builder.mType;
        mModules = builder.mModules;
    }

    public String getType() {
        return mType;
    }

    public long getTaskEffectTimeMillis() {
        return mTakeEffectTimeMillis;
    }

    public long getInvalidTimeMillis() {
        return mInvalidTimeMillis;
    }

    public HomeData.ItemInfoListBean getModuleData(String moduleType) {
        for (HomeData.ItemInfoListBean moduleData : mModules) {
            if (moduleType.equals(moduleData.itemType)) {
                return moduleData;
            }
        }
        return null;
    }

    public boolean isDefaultResourcePackage() {
        return TYPE_DEFAULT.equals(mType);
    }

    public HomeData.ItemInfoListBean getDataForModule(String moduleType) {
        return getModuleData(moduleType);
    }

    public List<String> getAllBitmapUriList() {
        List<String> uriList = new ArrayList<>();
        for (HomeData.ItemInfoListBean moduleData : mModules) {
            uriList.addAll(moduleData.getAllUriList());
        }
        return uriList;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("mType=").append(mType).append('\n');
        sb.append("mModules").append(mModules);
        return sb.toString();
    }

    public static class Builder {

        private String mType = TYPE_DEFAULT;

        private List<HomeData.ItemInfoListBean> mModules = new ArrayList<>();

        public Builder setType(String type) {
            mType = type;
            return this;
        }

        public Builder provideModule(HomeData.ItemInfoListBean data) {
            mModules.add(data);
            return this;
        }

        public ResourcePackage build() {
            return new ResourcePackage(this);
        }
    }
}
