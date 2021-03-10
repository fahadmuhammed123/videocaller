import React from 'react';
import JitsiMeetJS from "sariska-media-transport";
import {useDispatch} from "react-redux";
import {addTracks} from "../../store/actions/track";
import {SariskaNativeConnect} from "../../utils/SariskaNativeConnect";

const SariskaMediaTransport = (options={}) => {
    const dispatch = useDispatch();

    JitsiMeetJS.createLocalTracks({devices: ["video", "audio"], resolution: "180"}).then(tracks => {
        dispatch(addTracks(tracks));
        tracks.forEach(track=>SariskaNativeConnect.newLocalTrackMessage());
                     setLocalTracks([...videoTrack, ...audioTrack]);
                    const videoTrack = await SariskaMediaTransport.createLocalTracks({devices: ["video"], resolution: "180"});
                    const audioTrack = await SariskaMediaTransport.createLocalTracks({devices: ["audio"]});
                    if (room?.isJoined()) {
                        localTracks.forEach(track => room.addTrack(track).catch(err => console.log("track is already added")));
                    }
    }).catch((e) => console.log(e, "failed to fetch tracks"));

    return null;
}

export default SariskaMediaTransport;
