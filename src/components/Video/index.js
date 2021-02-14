import React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { RTCView} from 'react-native-webrtc';


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
    return <RTCView streamURL={props.track.stream?.toURL()} style={styles.video} />;
};

export default Video;
