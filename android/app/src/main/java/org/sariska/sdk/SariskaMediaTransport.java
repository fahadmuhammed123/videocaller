package org.sariska.sdk;

import android.os.Bundle;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.util.ArrayList;
import java.util.List;


class SariskaMediaTransport extends ReactContextBaseJavaModule {
    private static LocalTrackBinding localTrackBinding;

    SariskaMediaTransport(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "SariskaMediaTransport";
    }

    public static void init() {
        BroadcastNativeEvent.sendEvent("SARISKA_MEDIA_TRANSPORT_ACTION", Params.createParams("init"));
    }

    @ReactMethod
    public void newLocalTrackMessage(String action, ReadableArray tracks) {
        List<JitsiLocalTrack> tracksList = new ArrayList<>();
        for (int i = 0; i < tracks.size(); i++) {
            ReadableMap track = tracks.getMap(i);
            tracksList.add(new JitsiLocalTrack(track));
        }
        localTrackBinding.getCallback().onMessage(tracksList);
    }

    @ReactMethod
    public void newSariskaMediaTransportMessage(String action, ReadableMap readableMap) {

    }

    public static void createLocalTracks(Bundle options, final LocalTrackCallback callback) {
        localTrackBinding = new LocalTrackBinding("CREATE_LOCAL_TRACKS", callback);
        BroadcastNativeEvent.sendEvent("CREATE_LOCAL_TRACKS", Params.createTrackParams(options));
    }

    public static void setLogLevel() {

    }

    public static Connection JitsiConnection(String token) {
        return new Connection(token);
    }
}


