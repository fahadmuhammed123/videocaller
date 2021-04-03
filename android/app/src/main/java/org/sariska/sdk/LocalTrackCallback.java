package org.sariska.sdk;

import java.util.List;

public interface LocalTrackCallback {
    void onMessage(List<JitsiLocalTrack> track);
}
