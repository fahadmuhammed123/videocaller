package org.sariska.sdk;

import android.os.Bundle;
import com.facebook.react.ReactFragment;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


class Conference extends ReactContextBaseJavaModule {

    private final List<ConferenceBinding> bindingsConference = new ArrayList<>();

    private final List<RemoteTrackBinding> bindingsRemoteTrack = new ArrayList<>();

    Conference(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "Conference";
    }


    public Conference(Bundle options) {
        new ReactFragment.Builder()
                .setComponentName("Conference")
                .setLaunchOptions(options)
                .build();
    }
    
    @ReactMethod
    public void newConferenceMessage(String action, ReadableMap readableMap) {
        synchronized (bindingsConference) {
            for (final ConferenceBinding binding : bindingsConference ) {
                if (binding.getEvent().equals(action)) {
                    binding.getCallback().onMessage();
                    break;
                }
            }
        }
    }


    @ReactMethod
    public void newRemoteTrackMessage(String action, ReadableMap readableMap) {
        synchronized (bindingsRemoteTrack) {
            for (final RemoteTrackBinding binding : bindingsRemoteTrack) {
                if (binding.getEvent().equals(action)) {
                    binding.getCallback().onMessage(new JitsiRemoteTrack(readableMap));
                    break;
                }
            }
        }
    }

    private String role;

    private Participant participant;

    private String moderator;

    private String hidden;

    private String userId;

    public String isHidden() {
        return this.hidden;
    }

    public String isModerator () {
        return this.moderator;
    }

    public String myUserId() {
        return this.userId;
    }

    public String getRole() {
       return this.role;
    }

    public Participant getLocalUser() {
        return this.participant;
    }

    public void join() {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("join"));
    }

    public void join(String password) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("join", password));
    }

    public void  grantOwner(String id) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("grantOwner", id));
    }

    public void sendMessage(String message) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("sendMessage", message));
    }

    public void sendMessage(String message,  String to) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("sendMessage", message, to));
    }

    public void setLastN(int num) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("setLastN", num));
    }

    public void muteParticipant(String id,  String mediaType) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("muteParticipant", id, mediaType));
    }

    public void setDisplayName(String name) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("setDisplayName", name));
    }

    public void selectParticipant(String id) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("selectParticipant", id));
    }

    public void addTrack(JitsiLocalTrack track) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("addTrack", track.getId()));
    }

    public void removeTrack(JitsiLocalTrack track) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("removeTrack", track.getId()));
    }

    public void replaceTrack(JitsiLocalTrack oldTrack, JitsiLocalTrack newTrack) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("replaceTrack", oldTrack.getId(), newTrack.getId()));
    }

    public void lock(String password) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("lock", password));
    }

    public void setSubject(String subject) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("setSubject", subject));
    }

    public void unlock(){
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("unlock"));
    }

    public void kickParticipant(String id) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("kickParticipant", id));
    }

    public void pinParticipant(String id) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("pinParticipant", id));
    }

    public void startTranscriber() {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("startTranscriber"));
    }

    public void stopTranscriber() {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("stopTranscriber"));
    }

    public void revokeOwner(String participantId) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("revokeOwner", participantId));
    }

    public void startRecording(String mode, String streamId) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("startRecording", mode, streamId));
    }

    public void setLocalParticipantProperty(String propertyKey, String propertyValue) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION",  Params.createParams("setLocalParticipantProperty", propertyKey, propertyValue));
    }

    public void leave() {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION",  Params.createParams("leave"));
    }

    public Conference removeEventListener(final String event) {
        synchronized (bindingsConference) {
            for (final Iterator<ConferenceBinding> bindingIter = bindingsConference.iterator();
                 bindingIter.hasNext(); ) {
                if (bindingIter.next().getEvent().equals(event)) {
                    bindingIter.remove();
                    break;
                }
            }
            for (final Iterator<RemoteTrackBinding> bindingIter = bindingsRemoteTrack.iterator();
                 bindingIter.hasNext(); ) {
                if (bindingIter.next().getEvent().equals(event)) {
                    bindingIter.remove();
                    break;
                }
            }
        }
        return this;
    }

    public Conference addEventListener(final String event, final ConferenceCallback callback) {
        synchronized (bindingsConference) {
            this.bindingsConference.add(new ConferenceBinding(event, callback));
        }
        return this;
    }

    public Conference addEventListener(final String event, final RemoteTrackCallback callback) {
        synchronized (bindingsRemoteTrack) {
            this.bindingsRemoteTrack.add(new RemoteTrackBinding(event, callback));
        }
        return this;
    }
}
