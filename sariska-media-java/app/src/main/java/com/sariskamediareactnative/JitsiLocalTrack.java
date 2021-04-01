package org.sariska.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import org.json.JSONObject;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class JitsiLocalTrack extends EventEmitter {

    private String id;

    private String type;

    private String participantId;

    private String deviceId;

    private boolean muted;

    public void JitsiLocalTrack() {

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

    public Fragment render(JSONObject options) {
        Fragment reactNativeFragment = new ReactFragment.Builder()
            .setComponentName("Video")
            .setLaunchOptions(options)
            .build();
        return reactNativeFragment;
    }

    public void dispose() {
        BroadcastNativeEvent.sendEvent("LOCAL_TRACK_ACTION", Params.createParams("dispose", this.id));
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