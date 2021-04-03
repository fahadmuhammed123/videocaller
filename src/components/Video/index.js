import React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { RTCView} from 'react-native-webrtc';
import {useSelector} from "react-redux";


const styles = StyleSheet.create({
    video: {
        flex: 1,
        display: 'flex',
        flexDirection: 'row',
        height: 240,
        width: 360
    }
})

const Video = (props) => {
    const {id, isRemote} = props?.initialProps;
    if (!id) {
      return null;
    }
    let tracks= isRemote ? useSelector(state=>state.remoteTrack): useSelector(state=>state.localTrack);
    const track = tracks.find(track=>track.getId() === id);
    return <RTCView objectFit='cover' streamURL={track.stream?.toURL()} style={styles.video} />;
};

export default Video;
