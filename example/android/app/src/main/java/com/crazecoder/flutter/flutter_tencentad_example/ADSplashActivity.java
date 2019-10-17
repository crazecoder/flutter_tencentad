package com.crazecoder.flutter.flutter_tencentad_example;

import android.graphics.Color;

import com.crazecoder.flutter.tencentad.SplashAbstractActivity;

public class ADSplashActivity extends SplashAbstractActivity {

    @Override
    protected String getAppId() {
        return "1101152570";
    }

    @Override
    protected String getPosId() {
        return "8863364436303842593";
    }


    @Override
    protected Integer getLaunchBackground() {
        return R.drawable.launch_background;
    }

    @Override
    protected Integer getAppIconId() {
        return null;
    }

    @Override
    protected Integer getAppIconBackgroundColor() {
        return Color.WHITE;
    }

}
