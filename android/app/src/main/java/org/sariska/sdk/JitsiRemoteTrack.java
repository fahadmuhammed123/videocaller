package org.sariska.sdk;

import android.os.Bundle;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.ReactFragment;

class JitsiRemoteTrack {

    private String type;

    private String participantId;

    private String id;

    private boolean muted;

    private String streamURL;

    public JitsiRemoteTrack(ReadableMap readableMap) {
        this.type = readableMap.getString("type");
        this.participantId = readableMap.getString("participantId");
        this.id = readableMap.getString("id");
        this.muted = readableMap.getBoolean("muted");
        this.streamURL = readableMap.getString("streamURL");
    }

    public String getType() {
        return this.type;
    }

    public String getStreamURL() {
        return this.streamURL;
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

    public ReactFragment render(Bundle options) {
        options.putString("id", this.id);
        options.putBoolean("isRemote", true);
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

    public void dispose() {
        BroadcastNativeEvent.sendEvent("REMOTE_TRACK_ACTION", Params.createParams("dispose", this.id));
    }
}
