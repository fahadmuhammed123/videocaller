package org.sariska.sdk;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Connection extends ReactContextBaseJavaModule {

    private final static List<ConnectionBinding> bindings = new ArrayList<>();

    Connection(ReactApplicationContext context) {
        super(context);
    }

    public Connection(String token) {
        SariskaMediaTransport.sendEvent("CREATE_CONNECTION", Params.createConnection(token));
    }

    @Override
    public String getName() {
        return "Connection";
    }

    @ReactMethod
    public void newConnectionMessage(String action) {
        for ( ConnectionBinding binding : bindings ) {
            if (binding.getEvent().equals(action)) {
                binding.getCallback().onMessage();
                break;
            }
        }
    }

    public Conference initJitsiConference() {
        return new Conference();
    }

    public void connect() {
        SariskaMediaTransport.sendEvent("CONNECTION_ACTION", Params.createParams("connect"));
    }

    public void disconnect() {
        SariskaMediaTransport.sendEvent("CONNECTION_ACTION", Params.createParams("disconnect"));
    }

    public void addFeature() {
        SariskaMediaTransport.sendEvent("CONNECTION_ACTION", Params.createParams("addFeature"));
    }

    public void removeFeature() {
        SariskaMediaTransport.sendEvent("CONNECTION_ACTION", Params.createParams("removeFeature"));
    }

    public Connection removeEventListener(final String event) {
            for (final Iterator<ConnectionBinding> bindingIter = bindings.iterator();
                 bindingIter.hasNext(); ) {
                if (bindingIter.next().getEvent().equals(event)) {
                    bindingIter.remove();
                    break;
                }
            }
        return this;
    }

    public Connection addEventListener(final String event, final ConnectionCallback callback) {
        bindings.add(new ConnectionBinding(event, callback));
        return this;
    }
}
