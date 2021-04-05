import React from 'react';
import { RTCView} from 'react-native-webrtc';
import {useSelector} from "react-redux";

const Video = (props) => {
    const {id, isRemote, mirror, objectFit, zOrder, height, width } = props?.initialProps;
    if (!id) {
      return null;
    }
    let tracks= isRemote ? useSelector(state=>state.remoteTrack): useSelector(state=>state.localTrack);
    const track = tracks.find(track=>track.getId() === id);
    return <RTCView zOrder={ zOrder === 0 ? 0 : 0 } mirror = { mirror === true } objectFit={ objectFit === 'cover' ? 'cover': 'contain' } streamURL={ track.stream?.toURL() } />;
};

export default Video;
