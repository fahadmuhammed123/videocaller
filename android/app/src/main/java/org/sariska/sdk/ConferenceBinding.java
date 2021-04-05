package org.sariska.sdk;

class ConferenceBinding {
    private final String event;
    private final ConferenceCallback callback;

    public ConferenceBinding(final String event, final ConferenceCallback callback) {
        this.event = event;
        this.callback = callback;
    }

    public String getEvent() {
        return event;
    }

    public ConferenceCallback getCallback() {
        return callback;
    }

}
