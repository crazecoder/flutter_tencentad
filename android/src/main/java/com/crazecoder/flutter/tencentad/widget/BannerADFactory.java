package com.crazecoder.flutter.tencentad.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.comm.util.AdError;

import java.util.Locale;
import java.util.Map;

import io.flutter.Log;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

import static com.crazecoder.flutter.tencentad.FlutterTencentadPlugin.appId;

public class BannerADFactory extends PlatformViewFactory implements View.OnClickListener,
        UnifiedBannerADListener {
    private static final String TAG = "BannerADFactory";
    private Activity activity;
    private UnifiedBannerView bv;
    private String posId;

    public BannerADFactory(MessageCodec<Object> createArgsCodec, Activity activity) {
        super(createArgsCodec);
        this.activity = activity;
    }

    @Override
    public PlatformView create(Context context, int i, Object o) {
        Map<String, Object> param = (Map<String, Object>) o;
        String posId = null;
        if (param.containsKey("posId")) {
            posId = param.get("posId").toString();
        }
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(posId)) {
            return null;
        }
        bv = getBanner(appId, posId);
        return new PlatformView() {

            @Override
            public View getView() {
                return bv;
            }

            @Override
            public void dispose() {
                bv.destroy();
            }
        };
    }

    private UnifiedBannerView getBanner(String appId, String posId) {
        if (this.bv != null && this.posId.equals(posId)) {
            ViewParent vp = this.bv.getParent();
            if (vp != null) {
                ((ViewGroup) vp).removeView(this.bv);
            }
            this.bv.loadAD();
            return this.bv;
        }
        this.posId = posId;
        this.bv = new UnifiedBannerView(activity, appId, posId, this);
        this.bv.loadAD();
//        bannerContainer.addView(bv, getUnifiedBannerLayoutParams());
        return this.bv;
    }

    /**
     * banner2.0规定banner宽高比应该为6.4:1 , 开发者可自行设置符合规定宽高比的具体宽度和高度值
     *
     * @return
     */
//    private FrameLayout.LayoutParams getUnifiedBannerLayoutParams() {
//        Point screenSize = new Point();
//        getWindowManager().getDefaultDisplay().getSize(screenSize);
//        return new FrameLayout.LayoutParams(screenSize.x,  Math.round(screenSize.x / 6.4F));
//    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onNoAD(AdError adError) {
        String msg = String.format(Locale.getDefault(), "onNoAD, error code: %d, error msg: %s",
                adError.getErrorCode(), adError.getErrorMsg());
        Log.i(TAG, msg);
    }

    @Override
    public void onADReceive() {
        Log.i(TAG, "onADReceive");
    }

    @Override
    public void onADExposure() {
        Log.i(TAG, "onADExposure");
    }

    @Override
    public void onADClosed() {
        Log.i(TAG, "onADClosed");
    }

    @Override
    public void onADClicked() {
        Log.i(TAG, "onADClicked");
    }

    @Override
    public void onADLeftApplication() {
        Log.i(TAG, "onADLeftApplication");
    }

    @Override
    public void onADOpenOverlay() {
        Log.i(TAG, "onADOpenOverlay");
    }

    @Override
    public void onADCloseOverlay() {
        Log.i(TAG, "onADCloseOverlay");
    }
}
