package org.sariska.sdk;

public interface Callback {

    void onMessage();

    void onMessage(final String envelope);

    void onMessage(final JitsiRemoteTrack envelope);

    void onMessage(final JitsiLocalTrack envelope);
}

