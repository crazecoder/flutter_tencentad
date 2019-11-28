import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class SplashADView extends StatelessWidget {
  final String posId;
  final int fetchDelay;
  final String appId;
  const SplashADView({Key key, this.appId, this.posId, this.fetchDelay = 0})
      : super(key: key);
  static const MethodChannel _channel =
      const MethodChannel('crazecoder/flutter_tencentad/SplashADFactory');
  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      // child: GestureDetector(
      //   onTapUp: (_detail) {
      //     print("$_detail");
      //     _click(detail: _detail);
      //   },
      child: Focus(
        autofocus: true,
        child: AndroidView(
          key: UniqueKey(),
          viewType: "splashADView",
          // gestureRecognizers: <Factory<OneSequenceGestureRecognizer>>[
          //   new Factory<OneSequenceGestureRecognizer>(
          //     () => new TapGestureRecognizer(),
          //   ),
          // ].toSet(),
          creationParamsCodec: const StandardMessageCodec(),
          creationParams: {
            "posId": posId,
            "appId": appId,
            "fetchDelay": fetchDelay,
          },
          // ),
        ),
      ),
    );
  }

  Future<Null> _click({
    TapUpDetails detail,
  }) async {
    Map map = {
      "dx": detail.localPosition.dx,
      "dy": detail.localPosition.dy,
    };
    await _channel.invokeMethod('click', map);
  }
}
