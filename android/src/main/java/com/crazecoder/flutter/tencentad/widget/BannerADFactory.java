package com.crazecoder.flutter.tencentad.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.crazecoder.flutter.tencentad.utils.BannerUtil;
import com.qq.e.ads.banner2.UnifiedBannerView;

import java.util.Map;

import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

import static com.crazecoder.flutter.tencentad.FlutterTencentadPlugin.appId;

public class BannerADFactory extends PlatformViewFactory implements View.OnClickListener {
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
        this.bv = BannerUtil.getInstance(activity).getBanner(appId, posId);
        this.bv.loadAD();
//        bannerContainer.addView(bv, getUnifiedBannerLayoutParams());
        return this.bv;
    }

    @Override
    public void onClick(View v) {

    }

}
