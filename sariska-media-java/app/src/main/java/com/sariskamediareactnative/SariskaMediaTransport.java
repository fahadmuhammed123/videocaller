package org.sariska.sdk;
import java.util.ArrayList;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SariskaMediaTransport extends android.content.BroadcastReceiver {

    private final List<Binding> bindings = new ArrayList<>();

    public static void init(JSONObject options) {
        return new ReactFragment.Builder()
            .setComponentName("SariskaMediaTransport")
            .setLaunchOptions(options)
            .build();
    }

    public static void createLocalStream(JSONObject jsonObject,  final Callback callback) {
        synchronized (bindings) {
            this.bindings.add(new Binding("LOCAL_TRACK", callback));
        }
    }

    public static void setLogLevel() {

    }

    public static void JitsiConnection() {
         return Connection(ConnectionOptions options)
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        BroadcastEvent event = new BroadcastEvent(intent);
        Intent intent = getIntent();
        if (binding.getEvent().equals(intent.getStringExtra("key"))) {
            binding.getCallback().onMessage(new JitsiTrack(intent.getStringExtra("value")));
            break;
        }
    }

}


