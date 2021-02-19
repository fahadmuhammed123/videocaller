import React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { RTCView} from 'react-native-webrtc';
import {useSelector} from "react-redux";


const styles = StyleSheet.create({
    video: {
        flex: 1,
        display: 'flex',
        flexDirection: 'row',
        height: 180,
        width: 120,
        backgroundColor: 'red'
    }
})

const Video = (props) => {
    const {id} = props;
    const tracks = useSelector(state=>state.track);
    const track  =  tracks.find(track=>track.id===id);
    return <RTCView streamURL={track.stream?.toURL()} style={styles.video} />;
};

export default Video;
