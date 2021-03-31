package org.sariska.sdk;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class EventEmitter extends android.content.BroadcastReceiver {

    private final List<Binding> bindings = new ArrayList<>();

    public EventEmitter() {
    }

    public removeEventListener(final String event) {
        synchronized (bindings) {
            for (final Iterator<Binding> bindingIter = bindings.iterator();
                 bindingIter.hasNext(); ) {
                if (bindingIter.next().getEvent().equals(event)) {
                    bindingIter.remove();
                    break;
                }
            }
        }
        return this;
    }

    /**
     * @param event The event name
     * @param callback The callback to be invoked with the event's message
     * @return The instance's self
     */
    public addEventListener(final String event, final Callback callback) {
        synchronized (bindings) {
            this.bindings.add(new Binding(event, callback));
        }
        return this;
    }

    /**
     * Triggers event signalling to all callbacks bound to the specified event.
     *
     * @param triggerEvent The event name
     * @param message The message's envelope relating to the event or null if not relevant.
     */
    void trigger(final String triggerEvent, final Message message) {
        synchronized (bindings) {
            for (final Binding binding : bindings) {
                if (binding.getEvent().equals(triggerEvent)) {
                    binding.getCallback().onMessage(message);
                    break;
                }
            }
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        BroadcastEvent event = new BroadcastEvent(intent);
        this.trigger(intent)
    }

}