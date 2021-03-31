package org.sariska.sdk;
import com.facebook.react.ReactActivity;

import org.json.JSONObject;

public class MainActivity extends ReactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject options = new JSONObject();
        options.put("token", "eyJhbGciOiJSUzI1NiIsImtpZCI6IjI0ZmQ2ZjkyZDZkMDE3NDkyZTNlOThlMzM0ZWJhZmM3NmRkMzUwYmI5M2EwNzI5ZDM4IiwidHlwIjoiSldUIn0.eyJjb250ZXh0Ijp7InVzZXIiOnsiaWQiOiJpeHNpaWxxaSIsIm5hbWUiOiJxcXdlIn0sImdyb3VwIjoiZzdxa25rbTlhYnRwMW5hZ3Z5eTVmdSJ9LCJzdWIiOiIyIiwicm9vbSI6IjJ2bGk5ZTRnOWgiLCJpYXQiOjE2MTcxNzg3MzYsIm5iZiI6MTYxNzE3ODczNiwiaXNzIjoic2FyaXNrYSIsImF1ZCI6Im1lZGlhX21lc3NhZ2luZ19zYXJpc2thIiwiZXhwIjoxNjE3MjY1MTM2fQ.ATIy3SQSzCxah7JM0OYAJIBivdWYvx9yV0jp3LmypoS3z_Gy4R2KiP7O9Dttdjs5Y0BvYhNXBSfJjYSW5cyT_aCrJ9a3NAURzDEbx7PrMb3y555J0rMbaioS2ochvbvxoeetCSRPturtBenC7oLQLk_er498uAvjcVQzHIjyMigbXTUfQHTCSikePPPTshJSiq8jW7e_pMWCcGF1umcShcOSV5LIaTU4EdWyw8tDWXbXxbzRkHfy8nKncRc1-j5UTO105qmatEAJvMOTUX_J0jtBy37t32BV9bgtWCkEEECeACaeYvf882kr9b_W8cLiOvud0zUwHt2XhiW1scjM4Q");

        Connection connection = new SariskaMediaTransport.JitsiConnection(options);
        connection.addEventListener("CONNECTION_ESTABLISHED", Callback )
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

