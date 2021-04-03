package org.sariska.sdk;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactContext;

import androidx.annotation.Nullable;


class BroadcastNativeEvent extends ReactContextBaseJavaModule {

    private static ReactContext mReactContext;

    BroadcastNativeEvent(ReactApplicationContext context) {
        super(context);
        mReactContext = context;
    }

    public static void sendEvent(String eventName,
                                 @Nullable WritableMap params) {
        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    @Override
    public String getName() {
        return "BroadcastNativeEvent";
    }
}

