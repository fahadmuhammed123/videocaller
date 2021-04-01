package org.sariska.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import org.json.JSONObject;

public class JitsiRemoteTrack extends EventEmitter {

    private String type;

    private String ownerEndpointId;

    private String deviceId;

    private String id;

    private boolean muted;

    public void JitsiRemoteTrack() {

    }

    public String getType() {
        return this.type;
    }

    public String getId() {
        return this.id;
    }

    public void isMuted() {
        return this.muted;
    }

    public String getParticipantId() {
        return this.ownerEndpointId;
    }

    public boolean isLocal() {
        return false;
    }

    public void render(JSONObject options) {
        Fragment reactNativeFragment = new ReactFragment.Builder()
                .setComponentName("Video")
                .setLaunchOptions(options)
                .build();
        return reactNativeFragment;
    }

    public void dispose() {
        BroadcastNativeEvent.sendEvent("REMOTE_TRACK_ACTION", Params.createParams("dispose", this.id));
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