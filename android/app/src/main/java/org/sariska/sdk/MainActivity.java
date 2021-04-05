package org.sariska.sdk;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactFragment;
import com.oney.WebRTCModule.RTCVideoViewManager;
import com.oney.WebRTCModule.WebRTCView;

import java.util.List;

public class MainActivity extends ReactActivity {

    private Conference conference;

    private Connection connection;

    private ReactFragment fragment;

    private List<JitsiLocalTrack> localTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        SariskaMediaTransport.init(); // initialize sdk
        this.setupLocalStream();
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjI0ZmQ2ZjkyZDZkMDE3NDkyZTNlOThlMzM0ZWJhZmM3NmRkMzUwYmI5M2EwNzI5ZDM4IiwidHlwIjoiSldUIn0.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiJldHhsbG1jYSIsImF2YXRhciI6ImpramsiLCJuYW1lIjoiamtqayIsImVtYWlsIjoiamtqayJ9LCJncm91cCI6Imc3cWtua205YWJ0cDFuYWd2eXk1ZnUifSwic3ViIjoiMiIsInJvb20iOiJ3NjZ5emcyZXgiLCJpYXQiOjE2MTc2MTI2MTgsIm5iZiI6MTYxNzYxMjYxOCwiaXNzIjoic2FyaXNrYSIsImF1ZCI6Im1lZGlhX21lc3NhZ2luZ19zYXJpc2thIiwiZXhwIjoxNjE3Njk5MDE4fQ.Wc2bmQO1cL3b3m45BLOH5mD9ifC-0KRkLhDoTEmiNWbv01iuZ2ORn_6jjT7dr2e0iorodLiCYDft2GBZ_pdSp1oPiihX20Bl5fFHLa3ueJ_BjmDPzOondDIcdguOumuVwcBEr7TwMalNDlXb6UhERZ8dQ6vbpcErCi8L2UoCvfSQLvIDOpb0pONxJlL5rylCwbFEyoOEa44Oeo9wXRZfwtnKcAxe5VuSQUlONLhYMGqNJNitNA_k1E0Mz0ngPEYBxv5iCa3OGR1XXUKXDkENXFCCXDU-_VfZv9vwk6xjEkIs3KMRWzFkUKsEefutZMRC_tIZECLBYaDiVQ41wEVxGw";
        connection = SariskaMediaTransport.JitsiConnection(token);
        connection.addEventListener("CONNECTION_ESTABLISHED", this::createConference);
        connection.addEventListener("CONNECTION_FAILED", () -> {
            Log.i("CONNECTION_FAILED", "CONNECTION_FAILED");
        });
        connection.addEventListener("CONNECTION_DISCONNECTED", () -> {
            Log.i("CONNECTION_DISCONNECTED", "CONNECTION_DISCONNECTED");
        });
    }

    public void setupLocalStream() {
        Bundle options = new Bundle();
        options.putBoolean("audio", true);
        options.putBoolean("video", true);
        options.putInt("resolution", 240);  // 180, 240, 360, 720, 1080
        


        Bundle videoOptions = new Bundle();
        videoOptions.putString("objectFit", "cover");

        SariskaMediaTransport.createLocalTracks(options, tracks -> {
            localTracks = tracks;
            for (JitsiLocalTrack track : tracks) {
                if (track.getType().equals("video")) {
                    RTCVideoViewManager inst =  new RTCVideoViewManager();
                    inst.setStreamURL(inst, track.getStreamURL());
                    getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.local_video_view_container, track.render(videoOptions))
                        .commit();
                }
            }
        });
    }

    public void createConference() {

        conference = connection.initJitsiConference();

        conference.addEventListener("CONFERENCE_JOINED", () -> {
            for (JitsiLocalTrack track : localTracks) {
                conference.addTrack(track);
            }
        });

        conference.addEventListener("TRACK_ADDED", track -> {
            if (track.getType().equals("video")) {
                Bundle videoOptions = new Bundle();
                videoOptions.putString("objectFit", "cover");
                fragment = track.render(videoOptions);
                getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.remote_video_view_container, fragment)
                    .commit();
            }
        });

        conference.addEventListener("TRACK_REMOVED", track -> {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        });
    }


    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
        if (conference != null) {
            conference.leave();
            connection.disconnect();
        }
        finish();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( conference != null ) {
            conference.leave();
            connection.disconnect();
        }
        finish();
    }


    @Override
    protected String getMainComponentName() {
        return "SariskaMediaReactNative";
    }
}

