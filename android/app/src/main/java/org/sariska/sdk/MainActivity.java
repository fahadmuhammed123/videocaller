package org.sariska.sdk;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.widget.Button;
import android.view.View;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactFragment;

import java.util.List;

public class MainActivity extends ReactActivity {

    private Button mButton;

    private Conference conference;

    private Connection connection;

    private List<JitsiLocalTrack> localTracks;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        SariskaMediaTransport.init(); // initialize sdk
        this.setupLocalStream();
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjI0ZmQ2ZjkyZDZkMDE3NDkyZTNlOThlMzM0ZWJhZmM3NmRkMzUwYmI5M2EwNzI5ZDM4IiwidHlwIjoiSldUIn0.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiI1dGpmbWtnbCIsIm5hbWUiOiJqaiJ9LCJncm91cCI6Imc3cWtua205YWJ0cDFuYWd2eXk1ZnUifSwic3ViIjoiMiIsInJvb20iOiJtajR1d3k4bm1yIiwiaWF0IjoxNjE3NDU1MDU0LCJuYmYiOjE2MTc0NTUwNTQsImlzcyI6InNhcmlza2EiLCJhdWQiOiJtZWRpYV9tZXNzYWdpbmdfc2FyaXNrYSIsImV4cCI6MTYxNzU0MTQ1NH0.eS9QcYLONAujc2bj3DXDPkkibwFhEekR51u_Q4j0e2JWyGna36Ryn8c9Nlj37_Gw3iD80twEvuyfv6YF2Z0Mx2WrOw76j_V4aaytewqA8ZYsi0GO6tXF_HaNL_mjV7gW9ojG-0rrsEWYP68Lrsa0GE6YitNaZbZtxvixwMy1_1MDBxbafsQL4HkLoSRWq6N_6SoOqkAGGsOFVXvWkIXjp1FDuLB0Xt7U65jqiE4riFSmplHFoEX_G8ivs9YIsgvaS7xv47gpigBQ5dOPnmmCIrUfSFcu3-irf8t3LzMI2QCtWiE8UyBBj3g_B_1Xk16_GVwiRuMHBxPLqpq7YCeNPw";
        connection = SariskaMediaTransport.JitsiConnection(token);
        connection.addEventListener("CONNECTION_ESTABLISHED", this::createConference);
        connection.addEventListener("CONNECTION_FAILED", () -> {
            Log.i("CONNECTION_FAILED", "CONNECTION_FAILED");
        });
        connection.addEventListener("CONNECTION_DISCONNECTED", () -> {
            Log.i("CONNECTION_DISCONNECTED", "CONNECTION_DISCONNECTED");
        });
    }

    @SuppressLint("LongLogTag")
    public void setupLocalStream() {
        Bundle options = new Bundle();
        options.putBoolean("audio", true);
        options.putBoolean("video", true);
        options.putInt("resolution", 240);  // 180, 240, 360, 720, 1080
        SariskaMediaTransport.createLocalTracks(options, tracks -> {
            localTracks = tracks;
            for (JitsiLocalTrack track : tracks) {
                if (track.getType().equals("video")) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.local_video_view_container, track.render())
                            .commit();
                }
            }
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
            Log.i("TRACK_ADDED", "TRACK_ADDED");
            if (track.getType().equals("video")) {
                getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.remote_video_view_container, track.render())
                    .commit();
            }
        });

        conference.addEventListener("TRACK_REMOVED", track -> getSupportFragmentManager()
            .beginTransaction()
            .remove(getSupportFragmentManager().findFragmentById(R.id.remote_video_view_container))
            .commit());
    }


    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected String getMainComponentName() {
        return "SariskaMediaReactNative";
    }
}

