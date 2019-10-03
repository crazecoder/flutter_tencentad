import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BannerADView extends StatelessWidget {
  final String posId;
  final double width;

  const BannerADView({Key key, this.posId, this.width}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var _width = width ?? MediaQuery.of(context).size.width;
    return Container(
      width: _width,
      height: _width / 6.4,
      child: AndroidView(
        key: UniqueKey(),
        viewType: "bannerADView",
        creationParamsCodec: const StandardMessageCodec(),
        creationParams: {
          "posId": posId,
        },
      ),
    );
  }
}
