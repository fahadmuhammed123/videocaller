package org.sariska.sdk;


public class SariskaMediaTransport {

    public static void init(SDKOptions options) {
        return new ReactFragment.Builder()
                .setComponentName("SariskaMediaTransport")
                .setLaunchOptions(options)
                .build();
    }

    public static void createLocalTracks() {

    }

    public static void setLogLevel() {

    }

    public static void JitsiConnection() {
         return Connection(ConnectionOptions options)
    }

}