import 'package:flutter/material.dart';
import 'package:flutter_tencentad/flutter_tencentad.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  MyApp() {
    FlutterTencentad.initTencentAdSDK(appId: "1101152570");
    FlutterTencentad.showSplashAD(posId: "8863364436303842593");
  }
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: ListView.builder(
          itemCount: 150,
          itemBuilder: (BuildContext context, int index) {
            if(index%28==0){
              return NativeExpressADView(
              posId: "7030020348049331", count: 1,
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.width/1280*1100,
            );
            }
            return Image.network("http://pic.baike.soso.com/ugc/baikepic2/6150/cut-20181029112521-659563047_jpg_437_349_35847.jpg");
          },
        ),
      ),
    );
  }
}
