import React, {useEffect, useState} from 'react';
import SariskaMediaTransport from "sariska-media-transport";
import {conferenceConfig} from "../../constants";
import RemoteStream from "../RemoteStream";
import LocalStream from "../LocalStream";


const Conference = (props) => {
    const {connection} = props;
    const [room, setRoom] = useState(null);
    const [localTracks, setLocalTracks] = useState([]);
    const [remoteTracks, setRemoteTracks] = useState([]);

    useEffect(() => {
      const setupLocalStream = async ()=>{
            if (room?.isJoined() && localTracks.length) {
                return localTracks.forEach(track => room.addTrack(track).then(() => console.log("added")).catch(err => console.log("track is already added")));
            }
            const videoTrack = await SariskaMediaTransport.createLocalTracks({devices: ["video"], resolution: "180"});
            const audioTrack = await SariskaMediaTransport.createLocalTracks({devices: ["audio"]});
            setLocalTracks([...videoTrack, ...audioTrack]);
            if (room?.isJoined()) {
                localTracks.forEach(track => room.addTrack(track).catch(err => console.log("track is already added")));
            }
       }
       setupLocalStream();
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
            setRemoteTracks(tracks=>tracks.filter(item=>item.track.id !== track.track.id));
        }

        const onRemoteTrack = (track) => {
            if (!track || track.isLocal()) {
                return;
            }
            setRemoteTracks(tracks => [...tracks, track]);
        }

        const leave = (event) => {
            console.log("unloading.....");
            if (room?.isJoined()) {
                room.leave().then(() => connection.disconnect(event));
            }
        }

        room.on(SariskaMediaTransport.events.conference.CONFERENCE_JOINED, onConferenceJoined);
        room.on(SariskaMediaTransport.events.conference.TRACK_ADDED, onRemoteTrack);
        room.on(SariskaMediaTransport.events.conference.TRACK_REMOVED, onTrackRemoved);

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
