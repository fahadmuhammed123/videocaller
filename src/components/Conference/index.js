import React, {useEffect} from 'react';
import SariskaMediaTransport from "sariska-media-transport";
import {useSelector, useDispatch} from 'react-redux'
import {addRemoteTrack, removeRemoteTrack} from "../../store/actions/track";
import {setConference} from "../../store/actions/conference";
import {conferenceConfig} from "../../constants";


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
            SariskaNativeConnect.newConferenceMessage(SariskaMediaTransport.events.conference.CONFERENCE_JOINED);
        }

        const onTrackRemoved = (track) => {
            dispatch(removeRemoteTrack(track));
            SariskaNativeConnect.newRemoteTrackMessage(SariskaMediaTransport.events.conference.TRACK_REMOVED);
        }

        const onRemoteTrack = (track) => {
            if (!track || track.isLocal()) {
                return;
            }
            dispatch(addRemoteTrack(track));
            SariskaNativeConnect.newRemoteTrackMessage(SariskaMediaTransport.events.conference.TRACK_ADDED);
        }

        room.on(SariskaMediaTransport.events.conference.CONFERENCE_JOINED, onConferenceJoined);
        room.on(SariskaMediaTransport.events.conference.TRACK_ADDED, onRemoteTrack);
        room.on(SariskaMediaTransport.events.conference.TRACK_REMOVED, onTrackRemoved);

        return () => {
            room.off(SariskaMediaTransport.events.conference.CONFERENCE_JOINED, onConferenceJoined);
            room.off(SariskaMediaTransport.events.conference.TRACK_ADDED, onRemoteTrack);
            room.off(SariskaMediaTransport.events.conference.TRACK_REMOVED, onTrackRemoved);
        }
    }, [connection]);

    return null;
}

export default Conference;