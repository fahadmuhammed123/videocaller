package org.sariska.sdk;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import com.facebook.react.ReactFragment;

public class MainActivity extends FragmentActivity {
     private Button mButton;

     public Conference conference;

     public Connection connection;

     public JitsiLocalTrack[] localTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment reactNativeFragment = new ReactFragment.Builder()
                        .setComponentName("SariskaMediaReactNative")
                        .setLaunchOptions(getLaunchOptions("test message"))
                        .build();

                // pass the id from the <FrameLayout> and the name of the Fragment reference we created
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.remote_video_view_container, reactNativeFragment)
                        .commit();

            }
        });
        
       // Bundle options = new Bundle();
       // SariskaMediaTransport.init(options); // initialize sdk
       // this.setupLocalStream();

       // String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjI0ZmQ2ZjkyZDZkMDE3NDkyZTNlOThlMzM0ZWJhZmM3NmRkMzUwYmI5M2EwNzI5ZDM4IiwidHlwIjoiSldUIn0.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiJrZHJkam1wZCIsIm5hbWUiOiJkb21pbmFudF9ndWluZWFmb3dsIn0sImdyb3VwIjoiZzdxa25rbTlhYnRwMW5hZ3Z5eTVmdSJ9LCJzdWIiOiIyIiwicm9vbSI6IjlhN2w3aWlkYSIsImlhdCI6MTYxNzI2MzA3NiwibmJmIjoxNjE3MjYzMDc2LCJpc3MiOiJzYXJpc2thIiwiYXVkIjoibWVkaWFfbWVzc2FnaW5nX3Nhcmlza2EiLCJleHAiOjE2MTczNDk0NzZ9.SBbQ2GVPELcQzadJ-A3F-BfcZCtWR9SzwNR82CJebK7BvwhKo4wNQbE7l9eMM9Zh6ZaPRjtXRFMXmBnDBVpB4reJFfdL43PyJP_bC2B-FuLHzk4b8UgbQ_YjEBr_ueLKleYzRzfFR4_f6KIxXh-gYmbYb5-U8gbetLSzmhdIrEZsyp_4keP1DWTDFlEHEtzTJaRmc9BWUFkohWHXj7Nl2QhAbn5V2-LyXIBs5sxmxvFR_yVDd-ctKRfEgtUICDtWxKaa4-XI2TP8mJr4ONZrtvYDWHbmjDp1iDeg5S4MEOxBGrMTfzc9pGIPBLJYtlaQW0zixnrWto3UqOJMHG6sDA";
       // Connection connection = SariskaMediaTransport.JitsiConnection(token, options);

       // connection.addEventListener("CONNECTION_ESTABLISHED", this::createConference);

       // connection.addEventListener("CONNECTION_FAILED", () -> {

       // });

       // connection.addEventListener("CONNECTION_DISCONNECTED", () -> {

       // });
    }

    public void setupLocalStream() {
        Bundle options = new Bundle();
        options.putBoolean("audio", true);
        options.putBoolean("video", true);
        options.putInt("resolution", 180);  // 180, 240, 360, 720, 1080

        SariskaMediaTransport.createLocalTracks(options, tracks -> {
            localTracks = tracks;
            for ( JitsiLocalTrack track : tracks) {
                if (track.getType().equals("video")) {
                    getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.local_video_view_container, track.render())
                        .commit();
                }
            }
        });
    }


    private Bundle getLaunchOptions(String message) {
        Bundle initialProperties = new Bundle();
        initialProperties.putString("message", message);
        return initialProperties;
    }

    public void createConference() {
        Bundle options = new Bundle();
        Conference conference = connection.initJitsiConference(options);

        conference.addEventListener("CONFERENCE_JOINED", () -> {
            for ( JitsiLocalTrack track : localTracks) {
                conference.addTrack(track);
            }
        });

        conference.addEventListener("TRACK_ADDED", track -> {
            Fragment fragment = track.render();
            if (track.getType().equals("video")) {
                getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.remote_video_view_container, fragment)
                    .commit();
            }
        });

        conference.addEventListener("TRACK_REMOVED", track -> getSupportFragmentManager()
            .beginTransaction()
            .remove(getSupportFragmentManager().findFragmentById(R.id.remote_video_view_container))
            .commit());
    }

}

