package org.sariska.sdk;

import android.os.Bundle;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.util.ArrayList;
import java.util.List;


class SariskaMediaTransport extends ReactContextBaseJavaModule {

    private static LocalTrackCallback localTrackCallback;

    SariskaMediaTransport(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "SariskaMediaTransport";
    }

    public static void init(Bundle options) {
        BroadcastNativeEvent.sendEvent("SARISKA_MEDIA_TRANSPORT_ACTION", Params.createParams("init"));
    }

    @ReactMethod
    public void newLocalTrackMessage(String action, ReadableArray tracks) {
        List<JitsiLocalTrack> tracksList = new ArrayList<>();
        for (int i = 0; i < tracks.size(); i++) {
            ReadableMap track = tracks.getMap(i);
            tracksList.add(new JitsiLocalTrack(track));
        }
        localTrackCallback(tracksList);
    }

    private void localTrackCallback(List<JitsiLocalTrack> tracksList) {
    }

    @ReactMethod
    public void newSariskaMediaTransportMessage(String action, ReadableMap readableMap) {

    }

    public static void createLocalTracks(Bundle options, final LocalTrackCallback callback) {
        localTrackCallback = callback;
        BroadcastNativeEvent.sendEvent("SARISKA_MEDIA_TRANSPORT_ACTION", Params.createParams("createLocalTracks", options));
    }

    public static void setLogLevel() {

    }

    public static Connection JitsiConnection(String token, Bundle options) {
        return new Connection(options);
    }
}


