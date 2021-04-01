package org.sariska.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.json.JSONObject;


public class Conference extends EventEmitter {

    private String role;

    private Participant participant;

    private String moderator;

    private String hidden;

    private String userId;

    public void Conference(JSONObject options) {
        return new ReactFragment.Builder()
            .setComponentName("Conference")
            .setLaunchOptions(options)
            .build();
    }

    public void isHidden() {
        return this.hidden;
    }

    public boolean isModerator () {
        return this.moderator;
    }

    public void myUserId() {
        return this.userId;
    }

    public String getRole() {
       return this.role;
    }

    public void getLocalUser() {
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

    public void setLastN(Number number) {
        BroadcastNativeEvent.sendEvent("CONFERENCE_ACTION", Params.createParams("setLastN", number));
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

    @Override
    public void onReceive(Context context, Intent intent) {
        BroadcastEvent event = new BroadcastEvent(intent);
        Intent intent = getIntent();
        if (binding.getEvent().equals(intent.getStringExtra("key"))) {
            binding.getCallback().onMessage(new JitsiLocalTrack(intent.getStringExtra("value")));
            break;
        }
    }

}