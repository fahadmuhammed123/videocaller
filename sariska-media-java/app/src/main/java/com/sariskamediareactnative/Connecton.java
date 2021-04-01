package org.sariska.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.json.JSONObject;

public class Connection extends EventEmitter {

    public Connection(JSONObject options) {
        Fragment reactNativeFragment = new ReactFragment.Builder()
            .setComponentName("Connection")
            .setLaunchOptions(options)
            .build();
    }

    public void initJitsiConference(JSONObject options) {
        return new Conference(options);
    }

    public void connect() {
        BroadcastNativeEvent.sendEvent("CONNECTION_ACTION", Params.createParams("connect"));
    }

    public void disconnect() {
        BroadcastNativeEvent.sendEvent("CONNECTION_ACTION", Params.createParams("disconnect"));
    }

    public void addFeature() {
        BroadcastNativeEvent.sendEvent("CONNECTION_ACTION", Params.createParams("addFeature"));
    }

    public void removeFeature() {
        BroadcastNativeEvent.sendEvent("CONNECTION_ACTION", Params.createParams("removeFeature"));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        BroadcastEvent event = new BroadcastEvent(intent);
        Intent intent = getIntent();
        if (binding.getEvent().equals(intent.getStringExtra("key"))) {
            binding.getCallback().onMessage(new JitsiLocalTrack(intent.getStringExtra("value")));
            break;
        }
    }
}