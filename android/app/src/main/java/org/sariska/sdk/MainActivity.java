package org.sariska.sdk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import com.oney.WebRTCModule.WebRTCView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Conference conference;

    private Connection connection;

    private RelativeLayout mRemoteContainer;

    private RelativeLayout mLocalContainer;

    private List<JitsiLocalTrack> localTracks;

    private WebRTCView remoteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        SariskaMediaTransport.init(getApplication()); // initialize sdk
        mLocalContainer = findViewById(R.id.local_video_view_container);
        mRemoteContainer = findViewById(R.id.remote_video_view_container);

        this.setupLocalStream();
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjI0ZmQ2ZjkyZDZkMDE3NDkyZTNlOThlMzM0ZWJhZmM3NmRkMzUwYmI5M2EwNzI5ZDM4IiwidHlwIjoiSldUIn0.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiJsZmhhOTZoZSIsIm5hbWUiOiJyZWx1Y3RhbnRfc3BhcnJvdyJ9LCJncm91cCI6Imc3cWtua205YWJ0cDFuYWd2eXk1ZnUifSwic3ViIjoiMiIsInJvb20iOiJ5a3Qzd2txb2hkIiwiaWF0IjoxNjE3NzIzMTMxLCJuYmYiOjE2MTc3MjMxMzEsImlzcyI6InNhcmlza2EiLCJhdWQiOiJtZWRpYV9tZXNzYWdpbmdfc2FyaXNrYSIsImV4cCI6MTYxNzgwOTUzMX0.bSYpN8-Jk1anYnZ5cK8AkrmLJIEDZS2rQaOLOIluxjU9lGWMhAYIakbaVIK_zU4L1cm2hgDm7VYnZuKoJWZnP1aTvF1-qHHNuuZXV5g6kc-xkA9lkmDiMpGFFBTTRVvDKEley8Qw2ZT3S66MLKfiML8ONT1d1szfO9XV7epTrZikbeXIy3TpxoGo0IhgzYXT5iYmggr3Qg2Nw46vN0knqZfvoMEhOlQcYoKVOyO_j73M6_h_VjXc4aPUsvoEfKc84gWo1Nh8OVL-Tm33rYPG338clkusHwNtMaGFHK03afHhssqLscAGVFXhrMQy5nkZdsKEAubpdKDQ6RnAYN8IMQ";
        connection = SariskaMediaTransport.JitsiConnection(token);
        connection.addEventListener("CONNECTION_ESTABLISHED", this::createConference);
        connection.addEventListener("CONNECTION_FAILED", () -> {
            Log.i("CONNECTION_FAILED", "CONNECTION_FAILED");
        });
        connection.addEventListener("CONNECTION_DISCONNECTED", () -> {
            Log.i("CONNECTION_DISCONNECTED", "CONNECTION_DISCONNECTED");
        });
        connection.connect();
    }

    public void setupLocalStream() {
        Bundle options = new Bundle();
        options.putBoolean("audio", true);
        options.putBoolean("video", true);
        options.putInt("resolution", 240);  // 180, 240, 360, 720, 1080
//      options.putString("facingMode", "user");   user or environment
//      options.putBoolean("desktop", true);  for screen sharing
//      options.putString("micDeviceId", "mic_device_id");
//      options.putString("cameraDeviceId", "camera_device_id");

        SariskaMediaTransport.createLocalTracks(options, tracks -> {
            runOnUiThread(() -> {
                localTracks = tracks;
                for (JitsiLocalTrack track : tracks) {
                    if (track.getType().equals("video")) {
                        WebRTCView view = track.render();
                        view.setObjectFit("cover");
                        mLocalContainer.addView(view);
                    }
                }
            });
        });
    }


    @SuppressLint("LongLogTag")
    public void createConference() {

        conference = connection.initJitsiConference();

        conference.addEventListener("CONFERENCE_JOINED", () -> {
            for (JitsiLocalTrack track : localTracks) {
                conference.addTrack(track);
            }
        });

        conference.addEventListener("TRACK_ADDED", track -> {
            runOnUiThread(() -> {
                if (track.getType().equals("video")) {
                    Log.i("TRACK_ADDEDTRACK_ADDEDTRACK_ADDEDTRACK_ADDEDTRACK_ADDEDTRACK_ADDEDTRACK_ADDEDTRACK_ADDED","TRACK_ADDEDTRACK_ADDEDTRACK_ADDEDTRACK_ADDEDTRACK_ADDEDTRACK_ADDEDTRACK_ADDEDTRACK_ADDED");
                    WebRTCView view = track.render();
                    view.setObjectFit("cover");
                    remoteView = view;
                    mRemoteContainer.addView(view);
                }
            });
        });

        conference.addEventListener("TRACK_REMOVED", track -> {
            runOnUiThread(() -> {
                mRemoteContainer.removeView(remoteView);
            });
        });

        conference.join();
    }

    @Override
    public void onBackPressed() {
        conference.leave();
        connection.disconnect();
        finish();
        System.gc();
        System.exit(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        conference.leave();
        connection.disconnect();
        finish();

    }

}

