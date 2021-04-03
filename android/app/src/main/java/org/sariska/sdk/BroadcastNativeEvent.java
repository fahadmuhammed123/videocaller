package org.sariska.sdk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactContext;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


class BroadcastNativeEvent extends ReactContextBaseJavaModule {

    private static ReactContext mReactContext;
    private static final List<WritableMap> queue = new ArrayList<>();

    BroadcastNativeEvent(ReactApplicationContext context) {
        super(context);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                for (WritableMap item : queue) {
                    Log.i("loppp thrugh,,,,,,,,,,,,,,,,,,jijijijijijijijiijijijijiijijijijijijijijiiiiiiii", String.valueOf(item));
                    context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                      .emit(item.getString("eventName"), item);
                }
                mReactContext = context;
                queue.clear();
            }
        }, 2000);
    }


    public static void sendEvent(String eventName,
                                 @Nullable WritableMap params) {
        if (mReactContext != null) {
            mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        } else {
            params.putString("eventName", eventName);
            queue.add(params);
        }
    }

    @Override
    public String getName() {
        return "BroadcastNativeEvent";
    }
}

