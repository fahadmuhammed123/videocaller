package org.sariska.sdk;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;

public class SariskaConnectNative extends ReactContextBaseJavaModule {

    SariskaNativeConnect(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "SariskaConnectNative";
    }

    @ReactMethod
    public newConnectionMessage(String type, String value, String id) {
        val localIntent = Intent(type);
        localIntent.putExtra("value", value);
        localIntent.putExtra("id", id);
        localBroadcastManager.sendBroadcast(localIntent);
    }

    @ReactMethod
    public newConferenceMessage(String type, String value, String id) {
        val localIntent = Intent(type);
        localIntent.putExtra("value", value);
        localIntent.putExtra("id", id);
        localBroadcastManager.sendBroadcast(localIntent);
    }

    @ReactMethod
    public newTrackMessage(String type, String value, String id) {
        val localIntent = Intent(type);
        localIntent.putExtra("value", value);
        localIntent.putExtra("id", id);
        localBroadcastManager.sendBroadcast(localIntent);
    }

}