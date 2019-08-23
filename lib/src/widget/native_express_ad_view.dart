import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class NativeExpressADView extends StatelessWidget {
  final String posId;
  final int maxVideoDuration; //5-60
  final double width;
  final double height;
  final int count;

  const NativeExpressADView(
      {Key key,
      this.posId,
      this.count = 1,
      this.maxVideoDuration = 5,
      this.width,
      this.height})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: width ?? MediaQuery.of(context).size.width,
      height: height ?? 200,
      child: AndroidView(
        key: UniqueKey(),
        viewType: "nativeExpressADView",
        creationParamsCodec: const StandardMessageCodec(),
        creationParams: {
          "posId": posId,
          "adCount": count,
          "width":width,
          "height": height,
          "maxVideoDuration": maxVideoDuration,
        },
      ),
    );
  }
}
