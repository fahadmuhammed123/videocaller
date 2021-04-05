package org.sariska.sdk;

class LocalTrackBinding {
    private final String event;
    private final LocalTrackCallback callback;

    public LocalTrackBinding(final String event, final LocalTrackCallback callback) {
        this.event = event;
        this.callback = callback;
    }

    public String getEvent() {
        return event;
    }

    public LocalTrackCallback getCallback() {
        return callback;
    }

}
