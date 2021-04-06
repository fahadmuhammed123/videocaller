import SariskaMediaTransport from "sariska-media-transport";
import {addRemoteTrack, removeRemoteTrack} from "./src/store/actions/track";
import {addConference, removeConference} from "./src/store/actions/conference";
import {conferenceConfig} from "./src/constants";
import SariskaNativeConnect from "./src/utils/SariskaNativeConnect";
import {extractRemoteTrackInfo} from "./src/utils";
import {store} from './src/store/store';
import * as types from './src/store/actions/types';

export function createConference() {
    const state = store.getState();
    const room = state.connection.initJitsiConference(conferenceConfig);

    const onConferenceJoined = () => {
        const data = {
            role: room.getRole(),
            moderator: room.isModerator(),
            hidden: room.isHidden(),
            userId: room.myUserId(),
            participant: room.getLocalUser(),
        };
        SariskaNativeConnect.newConferenceMessage(types.CONFERENCE_JOINED, data);
    }

    const onTrackRemoved = (track) => {
        store.dispatch(removeRemoteTrack(track));
        SariskaNativeConnect.newRemoteTrackMessage(types.TRACK_REMOVED, extractRemoteTrackInfo(track));
    }

    const onTrackMuteChanged = (track) => {
        SariskaNativeConnect.newRemoteTrackMessage(types.TRACK_MUTE_CHANGED, extractRemoteTrackInfo(track));
    }

    const onConferenceLeft = () => {
        SariskaNativeConnect.newConferenceMessage(types.CONFERENCE_LEFT);
        store.dispatch(removeConference());
    }

    const onUserLeft = () => {
        console.log("user left");
    }

    const onRemoteTrack = (track) => {
        if (!track || track.isLocal()) {
            return;
        }
        console.log("onRemoteTrack");
        store.dispatch(addRemoteTrack(track));
        SariskaNativeConnect.newRemoteTrackMessage(types.TRACK_ADDED, extractRemoteTrackInfo(track));
    }

    room.on(SariskaMediaTransport.events.conference.CONFERENCE_JOINED, onConferenceJoined);
    room.on(SariskaMediaTransport.events.conference.CONFERENCE_LEFT, onConferenceLeft);
    room.on(SariskaMediaTransport.events.conference.TRACK_ADDED, onRemoteTrack);
    room.on(SariskaMediaTransport.events.conference.TRACK_REMOVED, onTrackRemoved);
    room.on(SariskaMediaTransport.events.conference.TRACK_MUTE_CHANGED, onTrackMuteChanged);
    room.on(SariskaMediaTransport.events.conference.USER_LEFT, onUserLeft);
    store.dispatch(addConference(room));
}

