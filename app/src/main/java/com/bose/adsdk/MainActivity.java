package com.bose.adsdk;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bose.adsdk.ads.FeedNativeActivity;
import com.bose.adsdk.ads.InterstitialActivity;
import com.bose.adsdk.ads.RewardActivity;
import com.bose.adsdk.ads.SplashActivity;
import com.bose.adsdk.databinding.ActivityMainBinding;
import com.ume.ads.common.managers.BSInitConfigHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gudd on 2023/11/22.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mBinding;

    private ButtonAdapter mAdapter;

    private List<String> mButtonNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setMyClick(this);
        String[] buttonNameArray = getResources().getStringArray(R.array.button_name);
        mButtonNames = Arrays.asList(buttonNameArray);
        mBinding.mediaId.setText("您申请的APP_KEY");
        initView();
    }

    private void initView() {
        mBinding.recyclerview.setHasFixedSize(true);

        mAdapter = new ButtonAdapter(mButtonNames);
        mBinding.recyclerview.setAdapter(mAdapter);

        mAdapter.setItemClickListener(position -> {
            String itemData = mAdapter.getItemData(position);
            if ("开屏广告".equals(itemData)) {
                SplashActivity.startActivity(this);
            } else if ("信息流广告".equals(itemData)) {
                FeedNativeActivity.startActivity(this);
            } else if ("插屏广告".equals(itemData)) {
                InterstitialActivity.startActivity(this);
            } else if ("激励视频广告".equals(itemData)) {
                RewardActivity.startActivity(this);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == mBinding.saveConfig) {
            BSInitConfigHelper.getInstance().setAppKey(mBinding.mediaId.getText().toString());
            Toast.makeText(this, "appID重置完成", Toast.LENGTH_SHORT).show();
        } else if (v == mBinding.clearMediaId) {
            mBinding.mediaId.setText("");
        }
    }
}