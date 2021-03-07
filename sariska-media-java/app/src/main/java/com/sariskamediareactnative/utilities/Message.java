package org.sariska.sdk;

public interface Callback {

    /**
     * @param the message payload and properties
     */
    void onMessage(final Message envelope);
}

