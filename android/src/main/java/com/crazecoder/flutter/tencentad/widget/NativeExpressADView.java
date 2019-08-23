package com.crazecoder.flutter.tencentad.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.pi.AdData;
import com.qq.e.comm.util.AdError;

import java.util.List;
import java.util.Map;

import io.flutter.Log;

import static com.crazecoder.flutter.tencentad.FlutterTencentadPlugin.appId;

public class NativeExpressADView extends FrameLayout implements NativeExpressAD.NativeExpressADListener {
    private static final String TAG = "NativeExpressADView";
    private NativeExpressAD mADManager;
    private Context context;
    private double width;
    private double height;

    public NativeExpressADView(@NonNull Context context, Map<String, Object> param) {
        super(context);
        this.context = context;
        int maxVideoDuration = 0;
        int adCount = 0;
        String posId = "";
        if (param.containsKey("posId")) {
            posId = param.get("posId").toString();
        }
        if (param.containsKey("maxVideoDuration")) {
            maxVideoDuration = (int) param.get("maxVideoDuration");
        }
        if (param.containsKey("adCount")) {
            adCount = (int) param.get("adCount");
        }
        if (param.containsKey("adCount")) {
            adCount = (int) param.get("adCount");
        }
        if (param.containsKey("width")) {
            width = (double) param.get("width");
            width = Dp2Px(context, (float) width);
        }
        if (param.containsKey("height")) {
            height = (double) param.get("height");
            height = Dp2Px(context, (float) height);
        }
        Log.e(TAG,"width = "+width);
        Log.e(TAG,"height = "+height);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int)width,(int)height);
        setLayoutParams(params);

        initNativeExpressAD(posId, maxVideoDuration, adCount);
    }
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    /**
     * 如果选择支持视频的模版样式，请使用{@link Constants#NativeExpressSupportVideoPosID}
     */
    private void initNativeExpressAD(String posId, int maxVideoDuration, int count) {
        if (TextUtils.isEmpty(posId)) return;
        ADSize adSize = new ADSize((int) width, ADSize.AUTO_HEIGHT); // 消息流中用AUTO_HEIGHT
        mADManager = new NativeExpressAD(context, adSize, appId, posId, this);
        mADManager.setMaxVideoDuration(maxVideoDuration);
        mADManager.loadAD(count);
    }

    private void show(com.qq.e.ads.nativ.NativeExpressADView adView) {
        addView(adView);
        adView.setMediaListener(mediaListener);
        adView.render();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG,"heightMeasureSpec = "+heightMeasureSpec);

    }

    @Override
    public void onNoAD(AdError adError) {
        Log.i(
                TAG,
                String.format("onNoAD, error code: %d, error msg: %s", adError.getErrorCode(),
                        adError.getErrorMsg()));
    }

    @Override
    public void onADLoaded(List<com.qq.e.ads.nativ.NativeExpressADView> adList) {
        Log.i(TAG, "onADLoaded: " + adList.size());
        show(adList.get(0));
    }

    @Override
    public void onRenderFail(com.qq.e.ads.nativ.NativeExpressADView adView) {
        Log.i(TAG, "onRenderFail: " + adView.toString());
    }

    @Override
    public void onRenderSuccess(com.qq.e.ads.nativ.NativeExpressADView adView) {
        Log.i(TAG, "onRenderSuccess: " + adView.toString() + ", adInfo: " + getAdInfo(adView));
    }

    @Override
    public void onADExposure(com.qq.e.ads.nativ.NativeExpressADView adView) {
        Log.i(TAG, "onADExposure: " + adView.toString());
    }

    @Override
    public void onADClicked(com.qq.e.ads.nativ.NativeExpressADView adView) {
        Log.i(TAG, "onADClicked: " + adView.toString());
    }

    @Override
    public void onADClosed(com.qq.e.ads.nativ.NativeExpressADView adView) {
        Log.i(TAG, "onADClosed: " + adView.toString());
        removeView(adView);

    }

    @Override
    public void onADLeftApplication(com.qq.e.ads.nativ.NativeExpressADView adView) {
        Log.i(TAG, "onADLeftApplication: " + adView.toString());
    }

    @Override
    public void onADOpenOverlay(com.qq.e.ads.nativ.NativeExpressADView adView) {
        Log.i(TAG, "onADOpenOverlay: " + adView.toString());
    }

    @Override
    public void onADCloseOverlay(com.qq.e.ads.nativ.NativeExpressADView adView) {
        Log.i(TAG, "onADCloseOverlay");
    }

    private String getAdInfo(com.qq.e.ads.nativ.NativeExpressADView nativeExpressADView) {
        AdData adData = nativeExpressADView.getBoundData();
        if (adData != null) {
            StringBuilder infoBuilder = new StringBuilder();
            infoBuilder.append("title:").append(adData.getTitle()).append(",")
                    .append("desc:").append(adData.getDesc()).append(",")
                    .append("patternType:").append(adData.getAdPatternType());
            if (adData.getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                infoBuilder.append(", video info: ")
                        .append(getVideoInfo(adData.getProperty(AdData.VideoPlayer.class)));
            }
            return infoBuilder.toString();
        }
        return null;
    }

    private String getVideoInfo(AdData.VideoPlayer videoPlayer) {
        if (videoPlayer != null) {
            StringBuilder videoBuilder = new StringBuilder();
            videoBuilder.append("state:").append(videoPlayer.getVideoState()).append(",")
                    .append("duration:").append(videoPlayer.getDuration()).append(",")
                    .append("position:").append(videoPlayer.getCurrentPosition());
            return videoBuilder.toString();
        }
        return null;
    }

    private NativeExpressMediaListener mediaListener = new NativeExpressMediaListener() {
        @Override
        public void onVideoInit(com.qq.e.ads.nativ.NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoInit: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoLoading(com.qq.e.ads.nativ.NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoLoading: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoReady(com.qq.e.ads.nativ.NativeExpressADView nativeExpressADView, long l) {
            Log.i(TAG, "onVideoReady: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoStart(com.qq.e.ads.nativ.NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoStart: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoPause(com.qq.e.ads.nativ.NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPause: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoComplete(com.qq.e.ads.nativ.NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoComplete: "
                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoError(com.qq.e.ads.nativ.NativeExpressADView nativeExpressADView, AdError adError) {
            Log.i(TAG, "onVideoError");
        }

        @Override
        public void onVideoPageOpen(com.qq.e.ads.nativ.NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPageOpen");
        }

        @Override
        public void onVideoPageClose(com.qq.e.ads.nativ.NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPageClose");
        }
    };

}
