package com.bose.adsdk.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bose.adsdk.R;
import com.bose.adsdk.ads.adapter.FeedNativeAdapter;
import com.bose.adsdk.ads.data.FeedAdData;
import com.bose.adsdk.ads.data.FeedDefaultData;
import com.bose.adsdk.ads.data.INativeAds;
import com.bose.adsdk.ads.data.UmeNativeAd;
import com.bose.adsdk.databinding.ActivityFeedNativeBinding;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ume.ads.common.util.AdError;
import com.ume.ads.common.util.BSLogger;
import com.ume.ads.sdk.BSAdSlot;
import com.ume.ads.sdk.nativ.BSNativeAdListener;
import com.ume.ads.sdk.nativ.NativeAd;
import com.ume.ads.sdk.nativ.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gudd on 2023/11/22.
 */
public class FeedNativeActivity extends AppCompatActivity implements View.OnClickListener {

    private List<MultiItemEntity> mData = new ArrayList<>();

    private ActivityFeedNativeBinding mBinding;
    private FeedNativeAdapter mAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FeedNativeActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_feed_native);
        mBinding.setMyClick(this);
        initAdapter();
        initPosId();
    }

    private void initPosId() {
        mBinding.posId.setText("信息流广告位ID");
    }

    private void initAdapter() {
        mAdapter = new FeedNativeAdapter(mData);
        mBinding.recyclerview.setHasFixedSize(true);
        mBinding.recyclerview.setAdapter(mAdapter);
    }

    private void loadNativeAd() {

        BSAdSlot adSlot = new BSAdSlot.Builder()
                .setAdId(mBinding.posId.getText().toString())
                .setAdViewAcceptedSize(0, 0)
                .build();
        NativeAd nativeAd = new NativeAd(this, adSlot, new BSNativeAdListener() {
            @Override
            public void onAdLoaded(List<NativeExpressAdView> nativeExpressAdViews) {
                if (nativeExpressAdViews != null && nativeExpressAdViews.size() > 0) {
                    BSLogger.i("信息流广告加载成功, 列表长度：" + nativeExpressAdViews.size());

                    mBinding.adContainer.post(() -> {
                        List<INativeAds> ads = new ArrayList<>();
                        for (NativeExpressAdView adView : nativeExpressAdViews) {
                            ads.add(new UmeNativeAd(adView));
                        }
                        refreshData(ads);
                    });

                }
            }

            @Override
            public void onAdFailed(AdError error) {
                BSLogger.e("信息流广告加载失败，errorCode=" + error.getErrorCode() + ", errorMsg=" + error.getErrorMsg());
            }
        });
        nativeAd.loadAd();
    }


    private void refreshData(List<INativeAds> adView) {

        List<MultiItemEntity> feedData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            FeedDefaultData feedDefaultData = new FeedDefaultData();
            feedDefaultData.setItemName("位置" + (i + 1));
            feedData.add(feedDefaultData);
        }

        int position = 2;
        for (int i = 0; i < feedData.size(); i++) {
            if (i < adView.size()) {
                INativeAds item = adView.get(i);
                FeedAdData feedAdData = new FeedAdData();
                feedAdData.setNativeAds(item);
                if (position <= feedData.size()) {
                    feedData.add(position, feedAdData);
                    position += 2;
                }
            }
        }
        mAdapter.setNewData(feedData);
    }


    @Override
    public void onClick(View v) {
        if (v == mBinding.clearPosId) {
            mBinding.posId.setText("");
        } else if (v == mBinding.loadAd) {
            loadNativeAd();
        }
    }
}