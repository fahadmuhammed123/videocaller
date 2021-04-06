package org.sariska.sdk;


import android.app.Application;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.facebook.react.PackageList;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;
import java.util.List;

class SariskaMediaTransport extends ReactContextBaseJavaModule {

    private static final List<WritableMap> queue = new ArrayList<>();
    private static LocalTrackBinding localTrackBinding;
    private static ReactContext mContext;

    @Override
    public String getName() {
        return "SariskaMediaTransport";
    }

    SariskaMediaTransport(ReactApplicationContext context) {
      super(context);
    }

    public static void sendEvent(String eventName, @Nullable WritableMap params) {
        if ( mContext == null ) {
            params.putString("eventName", eventName);
            queue.add(params);
            return;
        }
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    public static void init(Application application) {
        buildBundle(application);
        sendEvent("SARISKA_MEDIA_TRANSPORT_ACTION", Params.createParams("init"));
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
        ReactInstanceManagerBuilder builder = ReactInstanceManager.builder()
                .setApplication(application)
                .setJSBundleFile("index.android")
                .setBundleAssetName("index.android.bundle")
                .addPackages(new PackageList(application).getPackages())
                .addPackage(new SariskaPackageList())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.BEFORE_CREATE);
        ReactInstanceManager mReactInstanceManager = builder.build();
        mReactInstanceManager.createReactContextInBackground();
        mReactInstanceManager.addReactInstanceEventListener(context -> {
            mContext = context;
            for (WritableMap item : queue) {
                sendEvent(item.getString("eventName"), item);
            }
            queue.clear();
        });
    }

    public static void createLocalTracks(Bundle options, final LocalTrackCallback callback) {
        localTrackBinding = new LocalTrackBinding("CREATE_LOCAL_TRACKS", callback);
        sendEvent("CREATE_LOCAL_TRACKS", Params.createTrackParams(options));
    }

    public static void setLogLevel() {

    }

    public static ReactContext getReactContext() {
        return mContext;
    }

    public static Connection JitsiConnection(String token) {
        return new Connection(token);
    }
}

