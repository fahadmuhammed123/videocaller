package org.sariska.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.json.JSONObject;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class JitsiLocalTrack extends EventEmitter {

    private String type;

    private String participantId;

    private String deviceId;

    private String id;

    private Boolean remote;

    public void JitsiTrack() {

    }

    public String getType() {
        return this.remote;
    }

    public String getId() {
        return this.id;
    }

    public void mute() {
        BroadcastNativeEvent.sendEvent("mute", this.id);
    }

    public void unmute() {
        BroadcastNativeEvent.sendEvent("unmute", this.id);
    }

    public void getDeviceId() {
        return this.deviceId;
    }

    public String getParticipantId() {
        return this.participantId;
    }

    public void render(JSONObject options) {
        Fragment reactNativeFragment = new ReactFragment.Builder()
            .setComponentName("Video")
            .setLaunchOptions(options)
            .build();
        return reactNativeFragment;
    }

    public void dispose() {
        BroadcastNativeEvent.sendEvent("dispose", this.id);
    }
}