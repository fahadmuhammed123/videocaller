import SariskaMediaTransport from "sariska-media-transport";
import {addRemoteTrack, removeRemoteTrack} from "./src/store/actions/track";
import {addConference} from "./src/store/actions/conference";
import {conferenceConfig} from "./src/constants";
import SariskaNativeConnect from "./src/utils/SariskaNativeConnect";
import {extractTrackInfo} from "./src/utils";
import {store} from './src/store/store';

export function createConference() {
    const room = store.connection.initJitsiConference(conferenceConfig);
    room.join();

    const onConferenceJoined = () => {
        store.dispatch(addConference(room));
        const data = {
            role: room.getRole(),
            moderator: room.isModerator(),
            hidden: room.isHidden(),
            userId: room.myUserId(),
            participant: room.getLocalUser(),
        };
        SariskaNativeConnect.newConferenceMessage(SariskaMediaTransport.events.conference.CONFERENCE_JOINED, data);
    }

    const onTrackRemoved = (track) => {
        store.dispatch(removeRemoteTrack(track));
        SariskaNativeConnect.newRemoteTrackMessage(SariskaMediaTransport.events.conference.TRACK_REMOVED, extractTrackInfo(track));
    }

    const onTrackMuteChanged = (track) => {
        SariskaNativeConnect.newRemoteTrackMessage(SariskaMediaTransport.events.conference.TRACK_MUTE_CHANGED, extractTrackInfo(track));
    }

    const onRemoteTrack = (track) => {
        if (!track || track.isLocal()) {
            return;
        }
        store.dispatch(addRemoteTrack(track));
        SariskaNativeConnect.newRemoteTrackMessage(SariskaMediaTransport.events.conference.TRACK_ADDED, extractTrackInfo(track));
    }

    room.on(SariskaMediaTransport.events.conference.CONFERENCE_JOINED, onConferenceJoined);
    room.on(SariskaMediaTransport.events.conference.TRACK_ADDED, onRemoteTrack);
    room.on(SariskaMediaTransport.events.conference.TRACK_REMOVED, onTrackRemoved);
    room.on(SariskaMediaTransport.events.conference.TRACK_MUTE_CHANGED, onTrackMuteChanged);
}

