import React from 'react';
import SariskaMediaTransport from "sariska-media-transport";
import {useDispatch} from "react-redux";
import {SariskaNativeConnect} from "../../utils/SariskaNativeConnect";
import {initSDKConfig} from "../../constants";

const SariskaMediaTransport = (options=initSDKConfig) => {
    const dispatch = useDispatch();
    SariskaMediaTransport.init(initSDKConfig);
    SariskaMediaTransport.setLogLevel(SariskaMediaTransport.logLevels.ERROR); //TRACE ,DEBUG, INFO, LOG, WARN, ERROR

    SariskaMediaTransport.createLocalTracks({devices: ["video", "audio"], resolution: "180"}).then(tracks => {
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
