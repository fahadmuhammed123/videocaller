package org.sariska.sdk;

import android.os.Bundle;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.ReactFragment;

class JitsiRemoteTrack {

    private String type;

    private String participantId;

    private String deviceId;

    private String id;

    private boolean muted;

    public JitsiRemoteTrack(ReadableMap readableMap) {
        this.type = readableMap.getString("type");
        this.participantId = readableMap.getString("participantId");
        this.deviceId = readableMap.getString("deviceId");
        this.id = readableMap.getString("id");
        this.muted = readableMap.getBoolean("muted");
    }

    public String getType() {
        return this.type;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getId() {
        return this.id;
    }

    public boolean isMuted() {
        return this.muted;
    }

    public String getParticipantId() {
        return this.participantId;
    }

    public boolean isLocal() {
        return false;
    }

    public ReactFragment render() {
        Bundle options = new Bundle();
        options.putString("id", this.id);
        return new ReactFragment.Builder()
                .setComponentName("Video")
                .setLaunchOptions(options)
                .build();
    }

    public void dispose() {
        BroadcastNativeEvent.sendEvent("REMOTE_TRACK_ACTION", Params.createParams("dispose", this.id));
    }
}
