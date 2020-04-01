package com.crazecoder.flutter.tencentad.widget;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;

import java.util.Map;

import io.flutter.Log;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

import static com.crazecoder.flutter.tencentad.FlutterTencentadPlugin.appId;

public class SplashADFactory extends PlatformViewFactory implements SplashADListener {
    private SplashAD splashAD;

    private Activity activity;

    private FrameLayout viewGroup;

    private String posId;

    private MethodChannel channel;

    public SplashADFactory(MessageCodec<Object> createArgsCodec, PluginRegistry.Registrar registrar) {
        super(createArgsCodec);
        channel = new MethodChannel(registrar.messenger(), "crazecoder/flutter_tencentad/SplashADFactory");
        this.activity = registrar.activity();
    }

    @Override
    public PlatformView create(final Context context, int i, final Object o) {


        return new PlatformView() {
            @Override
            public View getView() {
                Map<String, Object> param = (Map<String, Object>) o;
                int fetchDelay = 0;
                if (param.containsKey("posId")) {
                    posId = param.get("posId").toString();
                }
                if (param.containsKey("fetchDelay")) {
                    fetchDelay = (int) param.get("fetchDelay");
                }
                viewGroup = new FrameLayout(context);
                viewGroup.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                fetchSplashAD(activity, viewGroup, appId, posId, SplashADFactory.this, fetchDelay);
                return viewGroup;
            }

            @Override
            public void dispose() {
            }
        };
    }

    private void setSimulateClick(View view, float x, float y) {
        Log.e("click","x="+x+"  y="+y);
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,
                MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }

    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity    展示广告的activity
     * @param adContainer 展示广告的大容器
     * @param appId       应用ID
     * @param posId       广告位ID
     * @param adListener  广告状态监听器
     * @param fetchDelay  拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     */
    private void fetchSplashAD(Activity activity, ViewGroup adContainer,
                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
        splashAD = new SplashAD(activity, appId, posId, adListener, fetchDelay);
        splashAD.fetchAndShowIn(adContainer);
    }

    @Override
    public void onADPresent() {
        Log.i("AD_DEMO", "SplashADPresent");
    }

    @Override
    public void onADClicked() {
        Log.i("AD_DEMO", "SplashADClicked clickUrl: "
                + (splashAD.getExt() != null ? splashAD.getExt().get("clickUrl") : ""));
    }

    /**
     * 倒计时回调，返回广告还将被展示的剩余时间。
     * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
     *
     * @param millisUntilFinished 剩余毫秒数
     */
    @Override
    public void onADTick(long millisUntilFinished) {
        Log.i("AD_DEMO", "SplashADTick " + millisUntilFinished + "ms");
    }

    @Override
    public void onADExposure() {
        Log.i("AD_DEMO", "SplashADExposure");
    }

    @Override
    public void onADLoaded(long l) {

    }

    @Override
    public void onADDismissed() {
        Log.i("AD_DEMO", "SplashADDismissed");
    }

    @Override
    public void onNoAD(AdError error) {
        Log.i(
                "AD_DEMO",
                String.format("LoadSplashADFail, eCode=%d, errorMsg=%s", error.getErrorCode(),
                        error.getErrorMsg()));
    }

}
