package com.crazecoder.flutter.flutter_tencentad_example;

import com.crazecoder.flutter.tencentad.SplashActivity;

public class ADSplashActivity extends SplashActivity {
    @Override
    protected String getAppId() {
        return "1101152570";
    }

    @Override
    protected String getPosId() {
        return "8863364436303842593";
    }


    @Override
    protected int getLaunchBackground() {
        return R.drawable.launch_background;
    }

}
