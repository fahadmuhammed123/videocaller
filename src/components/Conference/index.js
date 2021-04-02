import React, {useEffect} from 'react';
import SariskaMediaTransport from "sariska-media-transport";
import {useSelector, useDispatch} from 'react-redux'
import {addRemoteTrack, removeRemoteTrack} from "../../store/actions/track";
import {setConference} from "../../store/actions/conference";
import {conferenceConfig} from "../../constants";
import SariskaNativeConnect from "../../utils/SariskaNativeConnect";
import {extractTrackInfo} from "../../utils";

const Conference = ({options = conferenceConfig}) => {
    const connection = useSelector(state => state.connection);
    const dispatch = useDispatch();

    useEffect(() => {
        if (!connection) {
            return;
        }
        const room = connection.initJitsiConference(options);
        room.join();

        const onConferenceJoined = () => {
            dispatch(setConference(room));
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
            dispatch(removeRemoteTrack(track));
            SariskaNativeConnect.newRemoteTrackMessage(SariskaMediaTransport.events.conference.TRACK_REMOVED, extractTrackInfo(track));
        }

        const onTrackMuteChanged = (track) => {
            SariskaNativeConnect.newRemoteTrackMessage(SariskaMediaTransport.events.conference.TRACK_MUTE_CHANGED, extractTrackInfo(track));
        }

        const onRemoteTrack = (track) => {
            if (!track || track.isLocal()) {
                return;
            }
            dispatch(addRemoteTrack(track));
            SariskaNativeConnect.newRemoteTrackMessage(SariskaMediaTransport.events.conference.TRACK_ADDED, extractTrackInfo(track));
        }

        room.on(SariskaMediaTransport.events.conference.CONFERENCE_JOINED, onConferenceJoined);
        room.on(SariskaMediaTransport.events.conference.TRACK_ADDED, onRemoteTrack);
        room.on(SariskaMediaTransport.events.conference.TRACK_REMOVED, onTrackRemoved);
        room.on(SariskaMediaTransport.events.conference.TRACK_MUTE_CHANGED, onTrackMuteChanged);
        return () => {
            room.off(SariskaMediaTransport.events.conference.CONFERENCE_JOINED, onConferenceJoined);
            room.off(SariskaMediaTransport.events.conference.TRACK_ADDED, onRemoteTrack);
            room.off(SariskaMediaTransport.events.conference.TRACK_REMOVED, onTrackRemoved);
            room.off(SariskaMediaTransport.events.conference.TRACK_MUTE_CHANGED, onTrackMuteChanged);
        }
    }, [connection]);

    return null;
}

export default Conference;
