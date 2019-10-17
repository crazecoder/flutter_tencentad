import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

class FlutterTencentad {
  static const MethodChannel _channel =
      const MethodChannel('crazecoder/flutter_tencentad');
  static final StreamController<String> _streamController =
      StreamController<String>();

  static Future<Null> initTencentAdSDK(
      {@required String appId}) async {
    assert(appId != null && appId.isNotEmpty);
    Map map = {
      "appId": appId,
    };
    await _channel.invokeMethod('initTencentAdSDK', map);
  }

  static Future<Null> showSplashAD(
      {@required String posId, Function onSplashAdActivityResult}) async {
    assert(posId != null && posId.isNotEmpty);
    if (onSplashAdActivityResult != null) {
      _streamController.stream.listen((_data) {
        onSplashAdActivityResult();
      });
    }
    Map map = {
      "posId": posId,
    };
    await _channel.invokeMethod('showSplashAD', map);
  }

  static Future<Null> _handleMessages(MethodCall call) async {
    if (call.method == "onActivityResult")
      _streamController.add(call.arguments["activityName"]);
  }
}
