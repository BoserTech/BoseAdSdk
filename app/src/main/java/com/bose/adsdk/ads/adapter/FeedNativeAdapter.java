package com.bose.adsdk.ads.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Keep;

import com.bose.adsdk.R;
import com.bose.adsdk.ads.data.FeedAdData;
import com.bose.adsdk.ads.data.FeedDefaultData;
import com.bose.adsdk.ads.data.INativeAds;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by gudd on 2023/11/22.
 */
public class FeedNativeAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, FeedNativeAdapter.ContentHolder> {

    public static final int ITEM_DEFAULT = 0;
    public static final int ITEM_AD = 1;

    private List<MultiItemEntity> mData;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public FeedNativeAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(ITEM_DEFAULT, R.layout.item_default_of_feedlist);
        addItemType(ITEM_AD, R.layout.item_ad_of_feedlist);
    }

    @Override
    protected void convert(ContentHolder helper, MultiItemEntity item) {
        int itemType = item.getItemType();
        if (itemType == ITEM_DEFAULT) {
            FeedDefaultData itemData = (FeedDefaultData) item;
            helper.setText(R.id.item_name, itemData.getItemName());
        } else if (itemType == ITEM_AD) {
            FrameLayout itemView = helper.getView(R.id.adContainer);
            itemView.removeAllViews();

            FeedAdData itemData = (FeedAdData) item;
            INativeAds nativeAds = itemData.getNativeAds();
            if (nativeAds != null) {
                View adView = nativeAds.getView((Activity) mContext);
                if (adView != null) {
                    if (adView.getParent() instanceof ViewGroup) {
                        ViewGroup parent = (ViewGroup) adView.getParent();
                        parent.removeAllViews();
                    }
                    itemView.addView(adView);
                }
            }
        }
    }

    @Keep
    static class ContentHolder extends BaseViewHolder {

        public ContentHolder(View view) {
            super(view);
        }
    }

}
