package org.sariska.sdk;

import android.os.Bundle;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReadableMap;
import com.oney.WebRTCModule.WebRTCView;

class JitsiRemoteTrack extends ReactContextBaseJavaModule {

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

    public WebRTCView render() {
        WebRTCView view  = new WebRTCView(SariskaMediaTransport.getReactContext());
        view.setStreamURL(this.getStreamURL());
        return view;
    }

    public void dispose() {
        SariskaMediaTransport.sendEvent("REMOTE_TRACK_ACTION", Params.createParams("dispose", this.id));
    }

    @Override
    public String getName() {
        return "JitsiRemoteTrack";
    }
}
