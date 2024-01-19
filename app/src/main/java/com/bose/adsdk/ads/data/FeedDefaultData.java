package com.bose.adsdk.ads.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by gudd on 2023/11/22.
 */
public class FeedDefaultData implements MultiItemEntity {

    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public int getItemType() {
        return 0;// 0表示默认类型
    }
}
