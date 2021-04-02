package org.sariska.sdk;

import android.os.Bundle;
import com.facebook.react.ReactFragment;
import com.facebook.react.bridge.ReadableMap;

class JitsiLocalTrack {

    private String id;

    private String type;

    private String participantId;

    private String deviceId;

    private boolean muted;

    public JitsiLocalTrack(ReadableMap readableMap) {
        this.type = readableMap.getString("type");
        this.participantId = readableMap.getString("participantId");
        this.deviceId = readableMap.getString("deviceId");
        this.id = readableMap.getString("id");
        this.muted = readableMap.getBoolean("muted");
    }

    public boolean isLocal() {
        return true;
    }

    public boolean isMuted() {
        return this.muted;
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

    public void mute() {
        BroadcastNativeEvent.sendEvent("LOCAL_TRACK_ACTION", Params.createParams("mute", this.id));
    }

    public void unmute() {
        BroadcastNativeEvent.sendEvent("LOCAL_TRACK_ACTION", Params.createParams("unmute", this.id));
    }

    public void dispose() {
        BroadcastNativeEvent.sendEvent("LOCAL_TRACK_ACTION", Params.createParams("dispose", this.id));
    }

    public ReactFragment render() {
        Bundle options = new Bundle();
        options.putString("id", this.id);
        return new ReactFragment.Builder()
                .setComponentName("Video")
                .setLaunchOptions(options)
                .build();
    }
}
