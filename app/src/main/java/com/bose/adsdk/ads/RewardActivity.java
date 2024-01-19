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
import com.bose.adsdk.databinding.ActivityRewardBinding;
import com.ume.ads.common.util.AdError;
import com.ume.ads.common.util.BSLogger;
import com.ume.ads.sdk.reward.BSRewardAdListener;
import com.ume.ads.sdk.reward.RewardAd;

/**
 * Created by gudd on 2023/11/22.
 */
public class RewardActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRewardBinding mBinding;

    private RewardAd mRewardAd;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RewardActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reward);
        mBinding.setMyClick(this);
        initPosId();
    }

    private void initPosId() {
        mBinding.posId.setText("激励广告位ID");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRewardAd != null) {
            mRewardAd.destroy();
        }
    }

    private void loadRewardAd() {
        mRewardAd = new RewardAd(this, mBinding.posId.getText().toString(), true, new BSRewardAdListener() {
            @Override
            public void onAdLoad() {
                BSLogger.i("激励视频加载成功");
                mRewardAd.showAD();
                Toast.makeText(RewardActivity.this, "激励视频加载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailed(AdError adError) {
                BSLogger.e("激励视频加载失败，errCode=" + adError.getErrorCode() + ", errMsg:" + adError.getErrorMsg());
                Toast.makeText(RewardActivity.this, "激励视频广告加载失败," + adError.getErrorMsg() + ":" + adError.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdShow() {
                BSLogger.i("激励视频显示");
                Toast.makeText(RewardActivity.this, "激励视频显示", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdExpose() {
                BSLogger.i("激励视频曝光");
                Toast.makeText(RewardActivity.this, "激励视频曝光", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClick() {
                BSLogger.i("激励视频点击");
                Toast.makeText(RewardActivity.this, "激励视频点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClose() {
                BSLogger.i("激励视频关闭");
                Toast.makeText(RewardActivity.this, "激励视频关闭", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReward() {
                BSLogger.i("激励视频获得奖励");
                Toast.makeText(RewardActivity.this, "激励视频获得奖励", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoCache() {
                BSLogger.i("激励视频缓存成功");
                Toast.makeText(RewardActivity.this, "激励视频缓存成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoComplete() {
                BSLogger.i("激励视频播放完成");
                Toast.makeText(RewardActivity.this, "激励视频播放完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(AdError adError) {
                BSLogger.i("激励视频广告错误，" + adError.getErrorMsg() + ":" + adError.getErrorCode());
                Toast.makeText(RewardActivity.this, "激励视频广告错误，"+adError.getErrorMsg() + ":" + adError.getErrorCode(), Toast.LENGTH_SHORT).show();
            }
        });
        mRewardAd.loadAd();
    }

    @Override
    public void onClick(View v) {
        if (v == mBinding.clearPosId) {
            mBinding.posId.setText("");
        } else if (v == mBinding.getRewardAd) {
            loadRewardAd();
        }
    }
}