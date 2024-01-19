package com.bose.adsdk.ads.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by gudd on 2023/11/22.
 */
public class FeedAdData implements MultiItemEntity {

    private INativeAds nativeAds;

    public INativeAds getNativeAds() {
        return nativeAds;
    }

    public void setNativeAds(INativeAds nativeAds) {
        this.nativeAds = nativeAds;
    }

    @Override
    public int getItemType() {
        return 1;// 1:表示广告类型
    }
}
