package org.sariska.sdk;
import com.facebook.react.ReactActivity;
import org.json.JSONObject;

public class MainActivity extends ReactActivity {

     public Conference conference;

     public Connection connection;

     public JitsiLocalTrack[] localTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SariskaMediaTransport.init(); // initialize sdk
        this.setupLocalStream();
        JSONObject options = new JSONObject();
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjI0ZmQ2ZjkyZDZkMDE3NDkyZTNlOThlMzM0ZWJhZmM3NmRkMzUwYmI5M2EwNzI5ZDM4IiwidHlwIjoiSldUIn0.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiJrZHJkam1wZCIsIm5hbWUiOiJkb21pbmFudF9ndWluZWFmb3dsIn0sImdyb3VwIjoiZzdxa25rbTlhYnRwMW5hZ3Z5eTVmdSJ9LCJzdWIiOiIyIiwicm9vbSI6IjlhN2w3aWlkYSIsImlhdCI6MTYxNzI2MzA3NiwibmJmIjoxNjE3MjYzMDc2LCJpc3MiOiJzYXJpc2thIiwiYXVkIjoibWVkaWFfbWVzc2FnaW5nX3Nhcmlza2EiLCJleHAiOjE2MTczNDk0NzZ9.SBbQ2GVPELcQzadJ-A3F-BfcZCtWR9SzwNR82CJebK7BvwhKo4wNQbE7l9eMM9Zh6ZaPRjtXRFMXmBnDBVpB4reJFfdL43PyJP_bC2B-FuLHzk4b8UgbQ_YjEBr_ueLKleYzRzfFR4_f6KIxXh-gYmbYb5-U8gbetLSzmhdIrEZsyp_4keP1DWTDFlEHEtzTJaRmc9BWUFkohWHXj7Nl2QhAbn5V2-LyXIBs5sxmxvFR_yVDd-ctKRfEgtUICDtWxKaa4-XI2TP8mJr4ONZrtvYDWHbmjDp1iDeg5S4MEOxBGrMTfzc9pGIPBLJYtlaQW0zixnrWto3UqOJMHG6sDA";
        Connection connection = new SariskaMediaTransport.JitsiConnection(token, options);

        connection.addEventListener("CONNECTION_ESTABLISHED", new Callback() {
            @Override
            public void onMessage() {
               this.createConference();
            }
        });

        connection.addEventListener("CONNECTION_FAILED", new Callback() {
            @Override
            public void onMessage() {

            }
        });

        connection.addEventListener("CONNECTION_DISCONNECTED", new Callback() {
            @Override
            public void onMessage() {

            }
        });
    }

    public void setupLocalStream() {
        JSONObject options = new JSONObject();
        options.put("audio", true);
        options.put("video", true);
        options.put("resolution", 180);  // 180, 240, 360, 720, 1080

        SariskaMediaTransport.createLocalTracks(options,  new Callback(JitsiLocalTrack[] tracks) {
            localTracks = tracks;
            for ( JitsiLocalTrack track : localTracks) {
                if ( track.getType() === "video" ) {
                    getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.local_video_view_container, track.render())
                        .commit();
                }
            }
        });
    }

    public void createConference() {

        Conference conference = connection.initJitsiConference();

        conference.addEventListener("CONFERENCE_JOINED", new Callback() {
            @Override
            public void onMessage() {
                for ( JitsiLocalTrack track : localTracks) {
                    conference.addTrack(track);
                }
            }
        })

        conference.addEventListener("TRACK_ADDED", new Callback() {
            @Override
            public void onMessage(JitsiRemoteTrack track) {
                Fragment fragment = track.render();
                if ( track.getType() === "video" ) {
                    getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.remote_video_view_container, fragment)
                        .commit();
                }
            }
        })

        conference.addEventListener("TRACK_REMOVED", new Callback() {
            @Override
            public void onMessage(JitsiRemoteTrack track) {
                getSupportFragmentManager()
                    .beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentById(R.id.remote_video_view_container))
                    .commit();
            }
        })
    }
    /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule
     * rendering of the component.
     */

    @Override
    protected String getMainComponentName() {
        return "SariskaMediaReactNative";
    }

}

