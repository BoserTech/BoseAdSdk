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
import com.bose.adsdk.databinding.ActivityInterstitialBinding;
import com.ume.ads.common.util.AdError;
import com.ume.ads.common.util.BSLogger;
import com.ume.ads.sdk.interstitial.BSInterstitialAdListener;
import com.ume.ads.sdk.interstitial.InterstitialAd;

/**
 * Created by gudd on 2023/11/22.
 */
public class InterstitialActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityInterstitialBinding mBinding;

    private InterstitialAd mInterstitialAd;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, InterstitialActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_interstitial);
        mBinding.setMyClick(this);
        initPosId();
    }

    private void initPosId() {
        mBinding.posId.setText("插屏广告位ID");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInterstitialAd != null) {
            mInterstitialAd.destroy();
        }
    }

    private void loadInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this, mBinding.posId.getText().toString(), new BSInterstitialAdListener() {
            @Override
            public void onAdLoaded() {
                BSLogger.i("插屏加载成功");
                mInterstitialAd.showAd();
                Toast.makeText(InterstitialActivity.this, "插屏加载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailed(AdError error) {
                BSLogger.e("插屏加载失败，errorCode=" + error.getErrorCode() + ", errorMsg=" + error.getErrorMsg());
                Toast.makeText(InterstitialActivity.this, "插屏加载失败，errCode:"+error.getErrorCode()+", errMsg:"+error.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdExposed() {
                BSLogger.i("插屏曝光");
                Toast.makeText(InterstitialActivity.this, "插屏曝光", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() {
                BSLogger.i("插屏点击");
                Toast.makeText(InterstitialActivity.this, "插屏点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                BSLogger.i("插屏关闭");
                Toast.makeText(InterstitialActivity.this, "插屏关闭", Toast.LENGTH_SHORT).show();
            }
        });

        mInterstitialAd.loadAd();
    }

    @Override
    public void onClick(View v) {
        if (v == mBinding.clearPosId) {
            mBinding.posId.setText("");
        } else if (v == mBinding.getInterstitialAd) {
            loadInterstitialAd();
        }
    }
}