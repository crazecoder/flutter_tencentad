import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

class FlutterTencentad {
  static const MethodChannel _channel =
      const MethodChannel('crazecoder/flutter_tencentad');

  static Future<Null> initTencentAdSDK({@required String appId}) async {
    assert(appId != null && appId.isNotEmpty);
    Map map = {
      "appId": appId,
    };
    await _channel.invokeMethod('initTencentAdSDK', map);
  }
  static Future<Null> showSplashAD({@required String posId}) async {
    assert(posId != null && posId.isNotEmpty);
    Map map = {
      "posId": posId,
    };
    await _channel.invokeMethod('showSplashAD', map);
  }
}
