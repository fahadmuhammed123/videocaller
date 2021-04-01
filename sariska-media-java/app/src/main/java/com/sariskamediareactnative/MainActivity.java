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
        setContentView(R.layout.activity_main);
        SariskaMediaTransport.init(); // initialize sdk
        this.setupLocalStream();
        JSONObject options = new JSONObject();
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjI0ZmQ2ZjkyZDZkMDE3NDkyZTNlOThlMzM0ZWJhZmM3NmRkMzUwYmI5M2EwNzI5ZDM4IiwidHlwIjoiSldUIn0.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiJpeHNpaWxxaSIsIm5hbWUiOiJxcXdlIn0sImdyb3VwIjoiZzdxa25rbTlhYnRwMW5hZ3Z5eTVmdSJ9LCJzdWIiOiIyIiwicm9vbSI6IjJ2bGk5ZTRnOWgiLCJpYXQiOjE2MTcxNzg3MzYsIm5iZiI6MTYxNzE3ODczNiwiaXNzIjoic2FyaXNrYSIsImF1ZCI6Im1lZGlhX21lc3NhZ2luZ19zYXJpc2thIiwiZXhwIjoxNjE3MjY1MTM2fQ.ATIy3SQSzCxah7JM0OYAJIBivdWYvx9yV0jp3LmypoS3z_Gy4R2KiP7O9Dttdjs5Y0BvYhNXBSfJjYSW5cyT_aCrJ9a3NAURzDEbx7PrMb3y555J0rMbaioS2ochvbvxoeetCSRPturtBenC7oLQLk_er498uAvjcVQzHIjyMigbXTUfQHTCSikePPPTshJSiq8jW7e_pMWCcGF1umcShcOSV5LIaTU4EdWyw8tDWXbXxbzRkHfy8nKncRc1-j5UTO105qmatEAJvMOTUX_J0jtBy37t32BV9bgtWCkEEECeACaeYvf882kr9b_W8cLiOvud0zUwHt2XhiW1scjM4Q";

        Connection connection = new SariskaMediaTransport.JitsiConnection(token, options);
        connection.addEventListener("CONNECTION_ESTABLISHED", new Callback() {
            @Override
            public void onMessage(Envelope envelope) {
               this.createConference();
            }
        });

        connection.addEventListener("CONNECTION_FAILED", new Callback() {
            @Override
            public void onMessage(Envelope envelope) {

            }
        });

        connection.addEventListener("CONNECTION_DISCONNECTED", new Callback() {
            @Override
            public void onMessage(Envelope envelope) {

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
            public void onMessage(Envelope envelope) {
                for ( JitsiLocalTrack track : localTracks) {
                    conference.addTrack(track);
                }
            }
        })

        conference.addEventListener("TRACK_ADDED", new Callback(track) {
            @Override
            public void onMessage(Envelope envelope) {
                Fragment remoteTrack = track.render();
                getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.remote_video_view_container, remoteTrack)
                    .commit();
            }
        })

        conference.addEventListener("TRACK_REMOVED", new Callback(track) {
            @Override
            public void onMessage(Envelope envelope) {
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

