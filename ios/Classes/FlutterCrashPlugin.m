#import "FlutterCrashPlugin.h"
#import <Bugly/Bugly.h>

@implementation FlutterCrashPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"flutter_crash_plugin"
            binaryMessenger:[registrar messenger]];
    FlutterCrashPlugin* instance = [[FlutterCrashPlugin alloc] init];
    [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {

    if([@"setUp" isEqualToString:call.method]) {
        NSString *appID = call.arguments[@"app_id"];
        [Bugly startWithAppId:appID];
    } else if ([@"postException" isEqualToString:call.method]) {
      NSString *message = call.arguments[@"crash_message"];
      NSString *detail = call.arguments[@"crash_detail"];

      NSArray *stack = [detail componentsSeparatedByString:@"\n"];

      [Bugly reportExceptionWithCategory:4 name:message reason:stack[0] callStack:stack extraInfo:@{} terminateApp:NO];
      result(@0);
  }
  else {
    result(FlutterMethodNotImplemented);
  }
}


@end
