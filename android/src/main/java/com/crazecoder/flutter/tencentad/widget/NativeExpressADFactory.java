package com.crazecoder.flutter.tencentad.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class NativeExpressADFactory extends PlatformViewFactory  {
    public NativeExpressADFactory(MessageCodec<Object> createArgsCodec) {
        super(createArgsCodec);
    }

    @Override
    public PlatformView create(final Context context, int i, final Object o) {
        Map<String, Object> param = (Map<String, Object>) o;
        final NativeExpressADView adView = new NativeExpressADView(context,param);
        return new PlatformView() {
            @Override
            public View getView() {
                return adView;
            }

            @Override
            public void dispose() {

            }
        };
    }



}
