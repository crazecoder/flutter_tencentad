package com.crazecoder.flutter.tencentad.utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.comm.util.AdError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.flutter.Log;

public class BannerUtil implements UnifiedBannerADListener {
    private static final String TAG = "BannerUtil";
    private Activity activity;
    private Map<String, UnifiedBannerView> cache;
    private static BannerUtil instance;

    private BannerUtil(Activity activity) {
        this.activity = activity;
        cache = new HashMap<>();
    }

    public static BannerUtil getInstance(Activity activity) {
        if (instance == null) {
            instance = new BannerUtil(activity);
        }
        return instance;
    }

    public UnifiedBannerView getBanner(String appId, String posId) {
        UnifiedBannerView bv = new UnifiedBannerView(activity, appId, posId, this);
        cache.put(posId, bv);
        return bv;
    }

    public void show(String posId) {
        UnifiedBannerView bv = cache.get(posId);
        if (bv != null) {
            bv.loadAD();
            LinearLayout content = new LinearLayout(activity);
            content.setOrientation(LinearLayout.VERTICAL);
            content.setGravity(Gravity.BOTTOM);
            content.addView(bv,getUnifiedBannerLayoutParams());
            activity.addContentView(
                    content,
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public void dispose(String posId) {
        UnifiedBannerView bv = cache.get(posId);
        if (bv != null) {
            bv.destroy();
            View contentView = (View) bv.getParent();
            if (contentView == null || !(contentView.getParent() instanceof ViewGroup)) return;

            ViewGroup contentParent = (ViewGroup) (contentView.getParent());
            contentParent.removeView(contentView);
        }
    }

    private LinearLayout.LayoutParams getUnifiedBannerLayoutParams() {
        Point screenSize = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(screenSize);
        return new LinearLayout.LayoutParams(screenSize.x, Math.round(screenSize.x / 6.4F));
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
