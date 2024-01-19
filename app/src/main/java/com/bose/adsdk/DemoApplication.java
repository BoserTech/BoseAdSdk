package com.bose.adsdk;

import android.app.Application;

import com.ume.ads.common.managers.BSAdConfig;
import com.ume.ads.common.managers.BSAdSdk;
import com.ume.ads.common.managers.BSPhoneController;

/**
 * Created by gudd on 2023/11/22.
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initUmeSdk();
    }

    private void initUmeSdk() {

        BSAdConfig config = new BSAdConfig.Builder()
                .setAppKey("填写您申请的app_key")
                .setDebug(true)
                .setTimeout(5000)
                .setSupportMultiProcess(true)
                .setBSPhoneController(new BSPhoneController() {
                    @Override
                    public boolean canUsePhoneState() {
                        return true;
                    }
                }).build();

        BSAdSdk.init(this, config);
    }
}
