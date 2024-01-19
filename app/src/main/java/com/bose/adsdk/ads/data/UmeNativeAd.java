package com.bose.adsdk.ads.data;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ume.ads.common.util.AdError;
import com.ume.ads.common.util.BSLogger;
import com.ume.ads.sdk.nativ.BSNativeEventListener;
import com.ume.ads.sdk.nativ.NativeExpressAdView;
import com.ume.ads.sdk.nativ.NativeExpressMediaListener;

/**
 * Created by gudd on 2023/11/22.
 */
public class UmeNativeAd implements INativeAds {

    private NativeExpressAdView mAdData;

    private FrameLayout mAdView;

    private boolean isRender = false;

    public UmeNativeAd(NativeExpressAdView data) {
        this.mAdData = data;
        this.mAdData.setNativeEventListener(new BSNativeEventListener() {
            @Override
            public void onExposed() {
                BSLogger.i("Native on exposed.");
            }

            @Override
            public void onClicked() {
                BSLogger.i("Native on onClicked.");
            }

            @Override
            public void onDismiss() {
                BSLogger.i("Native on onDismiss.");
            }

            @Override
            public void onRenderSuccess() {
                BSLogger.i("Native on onRenderSuccess.");
            }

            @Override
            public void onRenderFailed() {
                BSLogger.i("Native on onRenderFailed.");
            }
        });
        this.mAdData.setMediaListener(new NativeExpressMediaListener() {
            @Override
            public void onVideoInit(NativeExpressAdView adView) {
                // 视频初始化成功（广点通预算时触发）
            }

            @Override
            public void onVideoLoading(NativeExpressAdView adView) {
                // 视频下载中（广点通预算时触发）
            }

            @Override
            public void onVideoCached(NativeExpressAdView adView) {
                // 视频下载完成（广点通预算时触发）
            }

            @Override
            public void onVideoReady(NativeExpressAdView adView, long duration) {
                // 视频播放器初始化完成，准备好可以播放了，videoDuration 是视频素材的时间长度，单位为 ms（广点通预算时触发）
            }

            @Override
            public void onVideoStart(NativeExpressAdView adView) {
                // 视频开始播放（广点通预算时触发）
            }

            @Override
            public void onVideoPause(NativeExpressAdView adView) {
                // 视频暂停（广点通预算时触发）
            }

            @Override
            public void onVideoComplete(NativeExpressAdView adView) {
                // 视频播放结束，手动调用 stop 或者自然播放到达最后一帧时都会触发（广点通预算时触发）
            }

            @Override
            public void onVideoError(NativeExpressAdView adView, AdError error) {
                // 视频播放时出现错误，error 对象包含了错误码和错误信息（广点通预算时触发）
            }

            @Override
            public void onVideoPageOpen(NativeExpressAdView adView) {
                // 进入视频落地页（广点通预算时触发）
            }

            @Override
            public void onVideoPageClose(NativeExpressAdView adView) {
                // 退出视频落地页（广点通预算时触发）
            }
        });
    }

    @Override
    public View getView(Activity activity) {
        if (!isRender) {
            if (mAdView == null) {
                mAdView = new FrameLayout(activity);
                mAdView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            mAdView.addView(mAdData.getView(activity));
            isRender = true;
        }
        return mAdView;
    }
}
