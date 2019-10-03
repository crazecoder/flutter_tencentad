package com.crazecoder.flutter.tencentad;

import android.app.Activity;
import android.content.Intent;

import com.crazecoder.flutter.tencentad.widget.BannerADFactory;
import com.crazecoder.flutter.tencentad.widget.NativeExpressADFactory;
import com.crazecoder.flutter.tencentad.widget.SplashADFactory;
import com.qq.e.ads.splash.SplashAD;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.plugin.common.StandardMessageCodec;

/**
 * FlutterTencentadPlugin
 */
public class FlutterTencentadPlugin implements MethodCallHandler {
    private Activity activity;
    public static String appId;
    private SplashAD splashAD;

    private FlutterTencentadPlugin(Activity activity) {
        this.activity = activity;
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "crazecoder/flutter_tencentad");
        NativeExpressADFactory nativeExpressADFactory = new NativeExpressADFactory(new StandardMessageCodec());
        SplashADFactory splashADFactory = new SplashADFactory(new StandardMessageCodec(), registrar);
        BannerADFactory bannerADFactory = new BannerADFactory(new StandardMessageCodec(),registrar.activity());

        registrar.platformViewRegistry().registerViewFactory("splashADView", splashADFactory);
        registrar.platformViewRegistry().registerViewFactory("nativeExpressADView", nativeExpressADFactory);
        registrar.platformViewRegistry().registerViewFactory("bannerADView", bannerADFactory);


        channel.setMethodCallHandler(new FlutterTencentadPlugin(registrar.activity()));

    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("initTencentAdSDK")) {
            if (call.hasArgument("appId")) {
                appId = call.argument("appId");
            }
            result.success(null);
        } else if (call.method.equals("showSplashAD")) {
            Intent intent = new Intent(activity, SplashActivity.class);
            intent.putExtra("appId", appId);
            if (call.hasArgument("posId")) {
                String posId = call.argument("posId").toString();
                preLoadSplashAD(posId);
                intent.putExtra("posId",posId);
                activity.startActivity(intent);
            }
            result.success(null);
        } else {
            result.notImplemented();
        }
    }
    private void preLoadSplashAD(String posId){
        splashAD = new SplashAD(activity, appId, posId,null);
        splashAD.preLoad();
    }
}
