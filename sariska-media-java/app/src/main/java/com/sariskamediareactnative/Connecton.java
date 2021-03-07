package org.sariska.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class Connection extends EventEmitter {

    public Connection(ConnectionOptions options) {
        Fragment reactNativeFragment = new ReactFragment.Builder()
                .setComponentName("Connection")
                .setLaunchOptions(options)
                .build();
    }

    public static void initJitsiConference(ConferenceOptions options) {
        return new Conference(options);
    }

    public void connect() {
        BroadcastNativeEvent.sendEvent("connect");
    }

    public void disconnect() {
        BroadcastNativeEvent.sendEvent("disconnect");
    }

    public void addFeature() {
        BroadcastNativeEvent.sendEvent("addFeature");
    }

    public void removeFeature() {
        BroadcastNativeEvent.sendEvent("removeFeature");
    }
}