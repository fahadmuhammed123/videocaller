import React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { RTCView } from 'react-native-webrtc';


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
    console.log("The stream URL is: "+ props.track.stream.toURL())
    //return <RTCView streamURL={props.src} mirror={true} style={styles.video} />;
    return <RTCView streamURL={props.track.stream?.toURL()} mirror={true} style={styles.video} />;
};

export default Video;
