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
    }).catch((e) => console.log(e, "failed to fetch tracks"));

    return null;
}

export default SariskaMediaTransport;
