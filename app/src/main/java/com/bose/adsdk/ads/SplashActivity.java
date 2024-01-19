package com.bose.adsdk.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bose.adsdk.R;
import com.bose.adsdk.databinding.ActivitySplashBinding;
import com.ume.ads.common.util.AdError;
import com.ume.ads.common.util.BSLogger;
import com.ume.ads.sdk.splash.BSSplashAdListener;
import com.ume.ads.sdk.splash.SplashAd;

/**
 * Created by gudd on 2023/11/22.
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySplashBinding mBinding;

    private SplashAd splashAd;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mBinding.setMyClick(this);
        initAdId();
    }

    private void initAdId() {
        mBinding.posId.setText("开屏广告位ID");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (splashAd != null) {
            splashAd.destroy();
        }
    }


    private void loadSplashAd() {
        splashAd = new SplashAd(this, mBinding.posId.getText().toString(), new BSSplashAdListener() {
            @Override
            public void onAdLoaded() {
                BSLogger.w("MainActivity 开屏加载成功，准备展示");
                if (splashAd != null) {
                    splashAd.show(mBinding.adContainer);
                }
                Toast.makeText(SplashActivity.this, "开屏加载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailed(AdError error) {
                BSLogger.e("开屏加载失败，errorCode=" + error.getErrorCode() + ", errorMsg=" + error.getErrorMsg());
                Toast.makeText(SplashActivity.this, "开屏加载失败，errorCode=" + error.getErrorCode() + ", errorMsg=" + error.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdExposed() {
                BSLogger.i("开屏广告曝光");
                Toast.makeText(SplashActivity.this, "开屏曝光成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() {
                BSLogger.i("开屏广告点击");
                Toast.makeText(SplashActivity.this, "开屏广告点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                BSLogger.i("开屏广告关闭");
                Toast.makeText(SplashActivity.this, "开屏广告关闭", Toast.LENGTH_SHORT).show();
            }
        });
        splashAd.loadAd();
    }

    @Override
    public void onClick(View v) {
        if (v == mBinding.clearPosId) {
            mBinding.posId.setText("");
        } else if (v == mBinding.getSplashAdBtn) {
            loadSplashAd();
        }
    }
}