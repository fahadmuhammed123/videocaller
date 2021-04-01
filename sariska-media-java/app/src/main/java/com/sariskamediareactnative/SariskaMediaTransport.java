package org.sariska.sdk;

import java.util.ArrayList;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

public class SariskaMediaTransport extends android.content.BroadcastReceiver {

    private final List<Binding> bindings = new ArrayList<>();

    public static void init(JSONObject options) {
        synchronized (bindings) {
            BroadcastNativeEvent.sendEvent("init", options);
        }
    }

    public static void createLocalTracks(JSONObject options,  final Callback callback) {
        synchronized (bindings) {
            this.bindings.add(new Binding("CREATE_LOCAL_TRACK",  callback));
            BroadcastNativeEvent.sendEvent("createLocalTracks", jsonObject);
        }
    }

    public static void setLogLevel() {

    }

    public static void JitsiConnection() {
         return Connection(ConnectionOptions options);
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


