import React, {useEffect} from 'react';
import SariskaMediaTransport from "sariska-media-transport";
import {useSelector, useDispatch} from 'react-redux'
import {store} from './src/store/store';
import {addRemoteTrack, removeRemoteTrack} from "../../store/actions/track";
import {addConference} from "../../store/actions/conference";
import {conferenceConfig} from "../../constants";
import SariskaNativeConnect from "../../utils/SariskaNativeConnect";
import {extractTrackInfo} from "../../utils";

const Conference = (props) => {
    const connection = useSelector(state => state.connection);
    const conference = useSelector(state => state.conference);
    const dispatch = useDispatch();

    useEffect(() => {
        if (!connection || conference) {
            return;
        }

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
