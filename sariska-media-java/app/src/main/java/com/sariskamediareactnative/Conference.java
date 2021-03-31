package org.sariska.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class Conference extends EventEmitter {

    private String id;

    private String role;

    private Participant participant;

    public void Conference(ConferenceOptions options) {
        return new ReactFragment.Builder()
            .setComponentName("Conference")
            .setLaunchOptions(options)
            .build();
    }

    public void join() {
        BroadcastNativeEvent.sendEvent("join");
    }

    public void  grantOwner(String id) {
        BroadcastNativeEvent.sendEvent("grantOwner", id);
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

    public void sendMessage(String message,  String to) {
        BroadcastNativeEvent.sendEvent("sendMessage", message);
    }

    public void setLastN(Number number) {
        BroadcastNativeEvent.sendEvent("setLastN", number);
    }

    public void muteParticipant(String id,  String mediaType) {
        BroadcastNativeEvent.sendEvent("muteParticipant", id);
    }

    public void setDisplayName(String name) {
        BroadcastNativeEvent.sendEvent("setDisplayName", name);
    }

    public void selectParticipant(String id) {
        BroadcastNativeEvent.sendEvent("selectParticipant", id);
    }

    public void addTrack(JitsiLocalTrack track) {
        BroadcastNativeEvent.sendEvent("addTrack");
    }

    public boolean isModerator () {

    }

    public void removeTrack(JitsiLocalTrack track) {
        BroadcastNativeEvent.sendEvent("removeTrack");
    }

    public void replaceTrack(JitsiLocalTrack oldTrack, JitsiLocalTrack newTrack) {
        BroadcastNativeEvent.sendEvent("replaceTrack");
    }

    public void lock(String password) {
        BroadcastNativeEvent.sendEvent("lock", password);
    }

    public void setSubject(String subject) {
        BroadcastNativeEvent.sendEvent("setSubject", subject);
    }

    public void unlock(){
        BroadcastNativeEvent.sendEvent("unlock");
    }

    public void kickParticipant(String id) {
        BroadcastNativeEvent.sendEvent("kickParticipant", id);
    }

    public void pinParticipant(String id) {
        BroadcastNativeEvent.sendEvent("pinParticipant", id);
    }

    public void startTranscriber() {
        BroadcastNativeEvent.sendEvent("startTranscriber");
    }

    public void stopTranscriber() {
        BroadcastNativeEvent.sendEvent("stopTranscriber");
    }

    public void revokeOwner(String participantId) {
        BroadcastNativeEvent.sendEvent("revokeOwner");
    }

    public void startRecording(String mode, String streamId) {
        BroadcastNativeEvent.sendEvent("startRecording")
    }

    public void setLocalParticipantProperty(propertyKey, propertyValue) {
        BroadcastNativeEvent.sendEvent("setLocalParticipantProperty")
    }

    public void isHidden() {
        BroadcastNativeEvent.sendEvent("isHidden")
    }

    public void leave() {
        BroadcastNativeEvent.sendEvent("leave");
    }

}