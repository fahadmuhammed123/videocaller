#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_REMAP_MODULE(SariskaConnectNative, SariskaConnectNativeModule, NSObject)

RCT_EXTERN_METHOD(newConnectionMessage:(NSString *)type value:(NSString *)value)
RCT_EXTERN_METHOD(newConferenceMessage:(NSString *)type value:(NSString *)value)
RCT_EXTERN_METHOD(newLocalTrackMessage:(NSString *)type value:(NSString *)value)
RCT_EXTERN_METHOD(sendCallbackToNative: (RCTResponseSenderBlock)rnCallback)


@end


@interface RCT_EXTERN_MODULE(BroadcastNativeEvent, RCTEventEmitter)
@end
