package com.hangchen.flutter_crash_plugin;

import com.tencent.bugly.crashreport.CrashReport;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterCrashPlugin */
public class FlutterCrashPlugin implements MethodCallHandler {
  public final Registrar registrar;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {

    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_crash_plugin");
    channel.setMethodCallHandler(new FlutterCrashPlugin(registrar));
  }

  private FlutterCrashPlugin(Registrar registrar) {
    this.registrar = registrar;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if(call.method.equals("setUp")) {
      String appID = call.argument("app_id");
      CrashReport.initCrashReport(registrar.activity().getApplicationContext(), appID, true);
      result.success(0);
    }
    else if(call.method.equals("postException")) {
      String message = call.argument("crash_message");
      String detail = call.argument("crash_detail");
      CrashReport.postException(4,"Flutter Exception",message,detail,null);
      result.success(0);
    }
    else {
      result.notImplemented();
    }
  }
}
