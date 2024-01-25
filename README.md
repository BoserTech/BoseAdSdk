## 博色广告 SDK
### 一、接入步骤
>您可以参考接入文档查看更细说明
#### 1. 接入准备
    接入SDK前，请联系商务同学获取应用APP_KEY、广告位ID等相关信息。
#### 2. SDK 集成
##### 2.1 aar 导入
```
repositories {
    flatDir {
        dirs 'libs'  
    }
}
depedencies {
    // 微米广告SDK依赖
    implementation(name: 'Lib_BS_Ads-x.x.x-release', ext: 'aar')
}
```

##### 2.2 依赖配置
```
implementation 'androidx.appcompat:appcompat:1.6.1'
// room
def room_version = "2.6.1"
implementation "androidx.room:room-runtime:$room_version"
annotationProcessor "androidx.room:room-compiler:$room_version"
// glide
implementation 'com.github.bumptech.glide:glide:4.9.0'
// okhttp
implementation 'com.squareup.okhttp3:okhttp:4.11.0'
```
##### 2.3 迁移至 AndroidX
SDK当前使用AndroidX支持库。如果您的工程项目非AndroidX，可参考官网[升级AndroidX](https://developer.android.google.cn/jetpack/androidx/migrate?hl=zh-cn)，请在 gradle.properties ⽂件中 新增如下配置。
```# Android 插件会使用对应的 AndroidX 库而非支持库。
android.useAndroidX=true
# Android 插件会通过重写现有第三方库的二进制文件，自动将这些库迁移为使用 AndroidX。
android.enableJetifier=true
```
>如果使用 Android Studio 3.2 及更高版本，您只需从菜单栏中依次选择 Refactor > Migrate to AndroidX，即可将现有项目迁移到 AndroidX。
#### 3. SDK初始化
请在您应用自定义Application类的onCreate()内调用以下方法进行SDK的初始化。
```
BSAdConfig config = new BSAdConfig.Builder()
        .setAppKey(appId)        // app_key请联系您的商务获取，必填
        .setDebug(true)          // 打开后可查看Log
        .setTimeout(5000)        // 超时时间，单位：毫秒
        .setSupportMultiProcess(true)
        .setBSPhoneController(new BSPhoneController() {
            @Override
            public boolean canUsePhoneState() {
                return true;
            }
        }).build();

BSAdSdk.init(this, config);        // 初始化SDK

BSAdSdk.getSdkVersion();           // 获取当前SDK版本号。例：V1.0.0
```
##### 3.1 BSPhoneController

|           方法名           |       类型       | 默认值  |                  说明                  |
|:-----------------------:|:--------------:|:----:|:------------------------------------:|
|     canUseLocation      |    boolean     | true |  允许读取位置信息，如有特殊需求返回false可禁止SDK读取位置信息  |
|    canUsePhoneState     |    boolean     | true | 允许读取手机状态信息，如有特殊需求返回false可禁止SDK读取设备信息 |
|       canUseOaid        |    boolean     | true | 允许获取手机Oaid，如有特殊需求返回false可禁止SDK获取Oaid |
| canUseInstalledPackages |    boolean     | true |  允许获取手机安装包列表，如有特殊需求返回false则禁止SDK获取。  |
|       getLocation       |    Location    | null |                                      |
|         getOaid         |     String     |  ""  |       必须为真实标识，格式错误或为空影响广告填充/收益       |
|         getImei         |     String     |  ""  |       必须为真实标识，格式错误或为空影响广告填充/收益       |
|         getImsi         |     String     |  ""  |                                      |
|      getAndroidId       |     String     |  ""  |             设备AndroidId              |
|    canUseMacAddress     |    boolean     | true |              允许获取Mac地址               |
|      getMacAddress      |     String     |  ""  |                                      |
|  getInstalledPackages   | List< String > | null |               获取安装包列表                |

### 二、广告获取
#### 1. Splash 广告
##### 1.1 请求示例
```
 Splash splashAd = new SplashAd(this, YOUR_POS_ID, new BSSplashAdListener() {
            @Override
            public void onAdLoaded() { 
                // 广告加载成功 
            }

            @Override
            public void onAdFailed(AdError error) { 
                // 广告加载失败 
            }

            @Override
            public void onAdExposed() { 
                // 广告曝光回调  
            }

            @Override
            public void onAdClicked() {
                // 广告点击回调
            }

            @Override
            public void onAdClosed() {
                // 广告关闭回调，可进行跳转主页面的处理
            }
        });
        splashAd.loadAd();
```

#### 2. 插屏广告
##### 2.1 请求示例
```
InterstitialAd mInterstitialAd = new InterstitialAd(this, YOUR_POS_ID, new BSInterstitialAdListener() {
            @Override
            public void onAdLoaded() {
                // 广告加载成功    
            }

            @Override
            public void onAdFailed(AdError error) {
                // 广告加载失败
            }

            @Override
            public void onAdExposed() {
                // 广告曝光回调
            }

            @Override
            public void onAdClicked() {
                // 广告点击回调
            }

            @Override
            public void onAdClosed() {
                // 广告关闭回调
            }
        });

 mInterstitialAd.loadAd();
```

#### 3. 信息流广告
##### 3.1 请求示例
```
BSAdSlot adSlot = new BSAdSlot.Builder()
                .setAdId(mBinding.posId.getText().toString())
                .setAdViewAcceptedSize(0, 0)
                .build();
NativeAd nativeAd = new NativeAd(this, adSlot, new UmeNativeAdListener() {
   @Override
   public void onAdLoaded(List<NativeExpressAdView> nativeExpressAdViews) {
        if (nativeExpressAdViews != null && nativeExpressAdViews.size() > 0) {
          // 广告加载成功
        }
    }

    @Override
        public void onAdFailed(AdError error) {
            // 广告加载失败
        }
    });
nativeAd.loadAd();
```

#### 4. 获取激励广告
```
RewardAd mRewardAd = new RewardAd(this, mBinding.posId.getText().toString(), true, new BSRewardAdListener() {
            @Override
            public void onAdLoad() {
                // 激励视频加载成功
                mRewardAd.showAD();
            }
            @Override
            public void onAdFailed(AdError adError) {
                 // 激励视频加载失败
            }
            @Override
            public void onAdShow() {
                 // 激励视频显示
            }
            @Override
            public void onAdExpose() {
                 // 激励视频曝光
            }
            @Override
            public void onAdClick() {
                 // 激励视频点击
            }
            @Override
            public void onAdClose() {
                 // 激励视频关闭
            }
            @Override
            public void onReward() {
                 // 激励视频获得奖励
            }
            @Override
            public void onVideoCache() {
                 // 激励视频缓存成功
            }
            @Override
            public void onVideoComplete() {
                 // 激励视频播放完成
            }
            @Override
            public void onError(AdError adError) {
                 // 激励视频广告错误
            }
        });
    mRewardAd.loadAd();
```
#### 5.获取Banner广告
```
BannerAd mBannerAd = new BannerAd(this, mBinding.posId.getText().toString(), new BSBannerAdListener() {
        @Override
       public void onAdLoad() {
            mBannerAd.showAD(mBinding.bannerContainer);
       }
       @Override
       public void onAdFailed(AdError adError) {
           Toast.makeText(BannerActivity.this, adError.getErrorMsg(), Toast.LENGTH_SHORT).show();
           BSLogger.i("banner ad load failed.code=" + adError.getErrorCode() + ", errMsg=" + adError.getErrorMsg());
       }
       @Override
       public void onAdExpose() {
           Toast.makeText(BannerActivity.this, "banner 曝光", Toast.LENGTH_SHORT).show();
           BSLogger.i("banner ad exposed.");
       }
       @Override
       public void onAdClick() {
           Toast.makeText(BannerActivity.this, "banner 点击", Toast.LENGTH_SHORT).show();
           BSLogger.i("banner ad clicked.");
       }
       @Override
       public void onAdClose() {
           Toast.makeText(BannerActivity.this, "banner 关闭", Toast.LENGTH_SHORT).show();
           BSLogger.i("banner ad close.");
       }
       });
mBannerAd.loadAd();
```
##### 5.1 BannerAd 横幅广告对象


### 错误码
|  错误码  |                    说明                     |            建议             |
|:-----:|:-----------------------------------------:|:-------------------------:|
| 10000 |                 SDK 未初始化                  | 根据 Logcat 中的错误信息提示修改嵌入代码  |
| 10001 | 初始化错误、包括广告位为空、AppKey为空、Context/Activity为空 | 根据 Logcat 中的错误信息提示修改嵌入代码  |
| 10002 |                   内部错误                    |      请将错误码和信息反馈给运营人员      |
| 12001 |                 广告配置请求异常                  |        请联系运营或技术人员         |
| 12002 |                  请求网络异常                   |        请联系运营或技术人员         |
| 12003 |              未获取到广告配置（响应体为空）              |                           |
| 12004 |               广告配置异常，或服务端异常               | 请把提供错误码和错误信息反馈给运营、商务或技术人员 |
| 12005 |                  广告配置为空                   |        请联系运营或技术人员         |
| 13000 |                   广告无填充                   |        多试几次或联系运营人员        |
| 13001 |                  广告请求超时                   |        多试几次或联系运营人员        |
| 13002 |                  广告发生错误                   |       广告在展示中可能出现的错误       |
| 13003 |                  视频发生错误                   |     一般出现于激励视频广告播放过程中      |

### 更新日志
|   版本   |     日期      | 说明                                                                  |
|:------:|:-----------:|:--------------------------------------------------------------------|
| 1.0.2  |  2024-1-18  | <br/>【增加】增加横幅广告</br> <br/>【优化】激励视频广告回调 </br> <br/>【BUG】解决bug。 </br> |
| 1.0.1  |  2024-1-18  | <br/>【增加】增加激励视频广告</br> <br/>【优化】广告点击统计分析</br><br/>【BUG】修复已知问题</br>  |
| 1.0.0  | 2023-11-25  | <br/>【优化】解决已知bug</br><br/>优化开屏、插屏、信息流</br>                          |