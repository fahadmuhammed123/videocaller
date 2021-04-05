package org.sariska.sdk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.ReactFragment;
import com.facebook.react.bridge.ReadableMap;

class JitsiLocalTrack {

    private String id;

    private String type;

    private String participantId;

    private String deviceId;

    private boolean muted;

    private String streamURL;

    public JitsiLocalTrack(ReadableMap readableMap) {
        this.type = readableMap.getString("type");
        this.participantId = readableMap.getString("participantId");
        this.deviceId = readableMap.getString("deviceId");
        this.id = readableMap.getString("id");
        this.muted = readableMap.getBoolean("muted");
        this.streamURL = readableMap.getString("streamURL");
    }

    public boolean isLocal() {
        return true;
    }

    public boolean isMuted() {
        return this.muted;
    }

    public String getStreamURL() {
        return this.streamURL;
    }

    public String getType() {
        return this.type;
    }

    public String getId() {
        return this.id;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getParticipantId() {
        return this.participantId;
    }

    public void switchCamera() {
       if (this.type == "video") {
          BroadcastNativeEvent.sendEvent("SWITCH_CAMERA", Params.createParams("switchCamera", this.id));
       }
    }

    public void mute() {
        BroadcastNativeEvent.sendEvent("LOCAL_TRACK_ACTION", Params.createParams("mute", this.id));
    }

    public void unmute() {
        BroadcastNativeEvent.sendEvent("LOCAL_TRACK_ACTION", Params.createParams("unmute", this.id));
    }

    public void dispose() {
        BroadcastNativeEvent.sendEvent("LOCAL_TRACK_ACTION", Params.createParams("dispose", this.id));
    }

    public ReactFragment render(Bundle options) {
        options.putBoolean("isRemote", false);
        options.putString("id", this.id);
        return new ReactFragment.Builder()
            .setComponentName("Video")
            .setLaunchOptions(options)
            .build();
    }

    public ReactFragment render() {
        Bundle options = new Bundle();
        options.putBoolean("isRemote", false);
        options.putString("id", this.id);
        return new ReactFragment.Builder()
            .setComponentName("Video")
            .setLaunchOptions(options)
            .build();
    }
}
