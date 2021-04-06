package org.sariska.sdk;

import android.os.Bundle;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReadableMap;
import com.oney.WebRTCModule.WebRTCView;

class JitsiLocalTrack extends ReactContextBaseJavaModule {

    private String id;

    private String type;

    private String participantId;

    private String deviceId;

    private boolean muted;

    private String streamURL;

    private ReactContext context;

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
       if (this.type.equals("video")) {
           SariskaMediaTransport.sendEvent("SWITCH_CAMERA", Params.createParams("switchCamera", this.id));
       }
    }

    public void mute() {
        SariskaMediaTransport.sendEvent("LOCAL_TRACK_ACTION", Params.createParams("mute", this.id));
    }

    public void unmute() {
        SariskaMediaTransport.sendEvent("LOCAL_TRACK_ACTION", Params.createParams("unmute", this.id));
    }

    public void dispose() {
        SariskaMediaTransport.sendEvent("LOCAL_TRACK_ACTION", Params.createParams("dispose", this.id));
    }

    public WebRTCView render() {
        WebRTCView view  = new WebRTCView(SariskaMediaTransport.getReactContext());
        view.setStreamURL(this.getStreamURL());
        return view;
    }

    @Override
    public String getName() {
        return "JitsiLocalTrack";
    }
}