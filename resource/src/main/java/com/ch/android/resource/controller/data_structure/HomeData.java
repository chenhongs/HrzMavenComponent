package com.ch.android.resource.controller.data_structure;

import com.ch.android.resource.Configuration;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeData implements Serializable {

    public String code;
    public List<ItemInfoListBean> itemInfoList;

    public static class ItemInfoListBean implements MultiItemEntity,Serializable {

        public String itemType;
        public String module;
        public List<ItemContentListBean> itemContentList;

        @Override
        public int getItemType() {

            if ("topBanner".equals(itemType)) {
                return Configuration.Constant.TYPE_TOP_BANNER;
            } else if ("iconList".equals(itemType)) {
                return Configuration.Constant.TYPE_ICON_LIST;
            } else if ("newUser".equals(itemType)) {
                return Configuration.Constant.TYPE_NEW_USER;
            } else if ("showEvent".equals(itemType)) {
                return Configuration.Constant.TYPE_SHOW_EVENT_3;
            } else if ("findGoodShop".equals(itemType)) {
                return Configuration.Constant.TYPE_FIND_GOOD_SHOP;
            }

            return Configuration.Constant.TYPE_TOP_BANNER;
        }

        public List<String> getAllUriList() {
            List<String> uriList = new ArrayList<>();
            for (ItemContentListBean b : itemContentList) {
                uriList.add(b.imageUrl);
            }
            return uriList;
        }

        public int getSpanSize() {
            return 4;
        }

        public static String getStringType(int numberType) {
            String strType = "topBanner";
            switch (numberType) {
                case Configuration.Constant.TYPE_TOP_BANNER:
                    strType = "topBanner";
                    break;
                case Configuration.Constant.TYPE_ICON_LIST:
                    strType = "iconList";
                    break;
                case Configuration.Constant.TYPE_NEW_USER:
                    strType = "newUser";
                    break;
                case Configuration.Constant.TYPE_SHOW_EVENT_3:
                    strType = "showEvent";
                    break;
                case Configuration.Constant.TYPE_FIND_GOOD_SHOP:
                    strType = "findGoodShop";
                    break;
            }
            return strType;
        }

        public static class ItemContentListBean implements MultiItemEntity,Serializable{

            public String itemType;

            public int sequence;
            public String title;
            public String url;
            public String startTime;
            public String endTime;
            public String img;
            public String subtitle;
            public long productId;
            public String productName;
            public double orgPrice;
            public double couponPrice;
            public double maxCommission;
            public int saleVolume;
            public int platform;


            public String imageUrl;
            public String clickUrl;
            public String itemTitle;
            public String itemSubTitle;
            public String itemSubscript;
            public String itemRecommendedLanguage;
            public String itemBackgroundImageUrl;
            public double ticketAmount;
            public int saleAmount;
            public double prePrice;
            public double discountPrice;
            public int typeId;
            public double rewardAmount;
            public String recommendTag;

            @Override
            public int getItemType() {
                if ("活动项目".equals(itemSubTitle)) {
                    return Configuration.Constant.TYPE_WATERFALL_ACTIVITY;
                }
                return Configuration.Constant.TYPE_WATERFALL_GOODS;
            }

            @Override
            public String toString() {
                return "ItemContentListBean{" +
                        "imageUrl='" + imageUrl + '\'' +
                        ", clickUrl='" + clickUrl + '\'' +
                        ", itemTitle='" + itemTitle + '\'' +
                        ", itemSubTitle='" + itemSubTitle + '\'' +
                        ", itemSubscript='" + itemSubscript + '\'' +
                        ", itemRecommendedLanguage='" + itemRecommendedLanguage + '\'' +
                        ", itemBackgroundImageUrl='" + itemBackgroundImageUrl + '\'' +
                        '}';
            }
        }


        @Override
        public String toString() {
            return "ItemInfoListBean{" +
                    "itemType='" + itemType + '\'' +
                    ", module='" + module + '\'' +
                    ", itemContentList=" + itemContentList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HomeIndex{" +
                "code='" + code + '\'' +
                ", itemInfoList=" + itemInfoList +
                '}';
    }
}
