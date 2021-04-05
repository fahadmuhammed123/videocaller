package org.sariska.sdk;

class ConnectionBinding {
    private final String event;
    private final ConnectionCallback callback;

    public ConnectionBinding(final String event, final ConnectionCallback callback) {
        this.event = event;
        this.callback = callback;
    }

    public String getEvent() {
        return event;
    }

    public ConnectionCallback getCallback() {
        return callback;
    }
}
