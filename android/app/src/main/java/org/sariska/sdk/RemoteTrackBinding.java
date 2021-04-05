package org.sariska.sdk;

class RemoteTrackBinding {
    private final String event;
    private final RemoteTrackCallback callback;

    public RemoteTrackBinding(final String event, final RemoteTrackCallback callback) {
        this.event = event;
        this.callback = callback;
    }

    public String getEvent() {
        return event;
    }

    public RemoteTrackCallback getCallback() {
        return callback;
    }
}
