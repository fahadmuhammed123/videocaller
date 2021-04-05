package org.sariska.sdk;

import android.app.Application;
import android.os.Bundle;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.LifecycleState;

import java.util.ArrayList;
import java.util.List;


class SariskaMediaTransport extends ReactContextBaseJavaModule {

    private static LocalTrackBinding localTrackBinding;

    private static ReactApplicationContext mContext;

    SariskaMediaTransport(ReactApplicationContext context) {
        super(context);
        buildBundle(context.getCurrentActivity().getApplication());
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

    public static void buildBundle(Application application) {
        try {
            ReactInstanceManagerBuilder builder = ReactInstanceManager.builder()
                .setApplication(application)
                .setJSMainModulePath("index.android")
                .setBundleAssetName("index.android.bundle")
                .addPackage(new SariskaPackageList())
                .setUseDeveloperSupport(false)
                .setInitialLifecycleState(LifecycleState.RESUMED);
            builder.build();
        } catch (Exception ignored) {
        }
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

