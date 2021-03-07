package org.sariska.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class JitsiTrack extends EventEmitter {

    private String type;

    private String participantId;

    private String deviceId;

    public String Id;

    private Boolean isRemote;

    public void Track() {

    }

    public String getType() {

    }

    public void isRemote() {
        return this.isRemote;
    }

    public void mute() {
        BroadcastNativeEvent.sendEvent("mute");
    }

    public String getId() {
        return this.Id;
    }

    public void unmute() {
        BroadcastNativeEvent.sendEvent("unmute");
    }

    public void getDeviceId() {
        return this.deviceId;
    }

    public String getParticipantId() {
        return this.participantId;
    }

    public void attach(FrameLayout container) {
        Fragment reactNativeFragment = new ReactFragment.Builder()
                .setComponentName("JitsiTrack")
                .setLaunchOptions(getLaunchOptions("test message"))
                .build();
        return reactNativeFragment;
    }

    public void dispose() {

    }

    public void detach() {

    }

}