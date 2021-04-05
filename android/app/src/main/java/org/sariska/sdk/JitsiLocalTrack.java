package org.sariska.sdk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.ReactFragment;
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

    public WebRTCView render() {
        WebRTCView view  = new WebRTCView(getReactApplicationContext());
        view.setStreamURL(this.getStreamURL());
        return view;
    }

    @Override
    public String getName() {
        return "JitsiLocalTrack";
    }
}
