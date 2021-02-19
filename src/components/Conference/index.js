import React, {useEffect, useState} from 'react';
import JitsiMeetJS from "sariska-media-transport";
import {conferenceConfig} from "../../constants";
import {useSelector, useDispatch} from "react-redux";
import {addConference} from "../../store/actions/conference";
import {addTrack, removeTrack} from "../../store/actions/track";
import {SariskaNativeConnect} from "../../utils/SariskaNativeConnect";

const Conference = (options={}) => {
    const dispatch = useDispatch();
    const connection = useSelector(state=>state.connection);

    useEffect(() => {
        if (!connection) {
            return;
        }

        const room = connection.initJitsiConference(conferenceConfig);
        room.join();

        const onConferenceJoined = () => {
            dispatch(addConference(room));
            SariskaNativeConnect.newConferenceMessage(JitsiMeetJS.events.conference.CONFERENCE_JOINED);
        }

        const onTrackRemoved = (track) => {
            dispatch(removeTrack(track));
            SariskaNativeConnect.newConferenceMessage(JitsiMeetJS.events.conference.TRACK_REMOVED);
        }

        const onRemoteTrack = (track) => {
            if (!track || track.isLocal()) {
                return;
            }
            dispatch(addTrack(track));
            SariskaNativeConnect.newConferenceMessage(JitsiMeetJS.events.conference.TRACK_ADDED);
        }

        const leave = (event) => {
            console.log("unloading.....");
            if (room?.isJoined()) {
                room.leave().then(() => connection.disconnect(event));
            }
        }

        room.on(JitsiMeetJS.events.conference.CONFERENCE_JOINED, onConferenceJoined);
        room.on(JitsiMeetJS.events.conference.TRACK_ADDED, onRemoteTrack);
        room.on(JitsiMeetJS.events.conference.TRACK_REMOVED, onTrackRemoved);

        return () => {
            leave();
        }
    }, [connection]);

    return null;
}

export default Conference;
