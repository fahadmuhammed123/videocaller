import React, {useEffect, useState} from 'react';
import JitsiMeetJS from "sariska-media-transport";
import {conferenceConfig} from "../../constants";
import RemoteStream from "../RemoteStream";
import LocalStream from "../LocalStream";


const Conference = (props) => {
    const {connection} = props;
    const [room, setRoom] = useState(null);
    const [localTracks, setLocalTracks] = useState([]);
    const [remoteTracks, setRemoteTracks] = useState([]);

    useEffect(() => {
        if (room && localTracks.length) {
            return localTracks.forEach(track => room.addTrack(track).then(() => console.log("added")).catch(err => console.log("track is already added")));
        }
        JitsiMeetJS.createLocalTracks({devices: ["video"], resolution: "180"}).then(tracks => {
            setLocalTracks(tracks);
            if (room?.isJoined()) {
                tracks.forEach(track => room.addTrack(track).catch(err => console.log("track is already added")));
            }
        }).catch((e) => console.log(e, "failed to fetch tracks"));
    }, [room]);

    useEffect(() => {
        const {connection} = props;
        if (!connection) {
            return;
        }

        const room = connection.initJitsiConference(conferenceConfig);
        room.join();

        const onConferenceJoined = () => {
            setRoom(room);
        }

        const onTrackRemoved = (track) => {
            setRemoteTracks(remoteTracks.filter(item => item.track.id !== track.track.id));
        }

        const onRemoteTrack = (track) => {
            if (!track || track.isLocal()) {
                return;
            }
            setRemoteTracks(remoteTracks => [...remoteTracks, track]);
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

    return (
        <>
            <RemoteStream remoteTracks={remoteTracks}/>
            <LocalStream localTracks={localTracks}/>
        </>
    )
}

export default Conference;
