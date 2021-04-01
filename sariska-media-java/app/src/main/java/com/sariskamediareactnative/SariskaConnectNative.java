package org.sariska.sdk;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class SariskaConnectNative extends ReactContextBaseJavaModule {

    SariskaConnectNative(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "SariskaConnectNative";
    }

    @ReactMethod
    public newConnectionMessage(String type) {
        val localIntent = Intent(type);
        localIntent.putExtra("key", type);
        localBroadcastManager.sendBroadcast(localIntent);
    }

    @ReactMethod
    public newConferenceMessage(String type, ReadableMap readableMap) {
        val localIntent = Intent(type);
        localIntent.putExtra("key", type);
        localIntent.putExtra("data", readableMap);
        localBroadcastManager.sendBroadcast(localIntent);
    }

    @ReactMethod
    public newLocalTrackMessage(String type, ReadableArray readableArray) {
        val localIntent = Intent(type);
        localIntent.putExtra("key", type)
        localIntent.putExtra("data", readableArray);
        localBroadcastManager.sendBroadcast(localIntent);
    }

    @ReactMethod
    public newRemoteTrackMessage(String type, ReadableMap readableMap) {
        val localIntent = Intent(type);
        localIntent.putExtra("key", type);
        localBroadcastManager.sendBroadcast(localIntent);
    }


    @ReactMethod
    public newSariskaMediaTransportMessage(String type, String value, String id) {
        val localIntent = Intent(type);
        localIntent.putExtra("key", type);
        localBroadcastManager.sendBroadcast(localIntent);
    }
}
