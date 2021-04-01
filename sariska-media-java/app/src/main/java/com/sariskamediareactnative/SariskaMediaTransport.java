package org.sariska.sdk;

import java.util.ArrayList;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SariskaMediaTransport extends EventEmitter {

    public static void init(JSONObject options) {
        BroadcastNativeEvent.sendEvent("SARISKA_MEDIA_TRANSPORT_ACTION", Params.createParams("init"));
    }

    public static void createLocalTracks(JSONObject options,  final Callback callback) {
        BroadcastNativeEvent.sendEvent("SARISKA_MEDIA_TRANSPORT_ACTION", Params.createParams("createLocalTracks", options));
    }

    public static void setLogLevel() {

    }

    public static void JitsiConnection(JSONObject options) {
        return Connection(options);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        BroadcastEvent event = new BroadcastEvent(intent);
        Intent intent = getIntent();

        if (intent.getStringExtra("key") === "createLocalTracks") {

        }

        if (binding.getEvent().equals(intent.getStringExtra("key"))) {
            binding.getCallback().onMessage(new JitsiLocalTrack(intent.getStringExtra("value")));
            break;
        }
    }
}


