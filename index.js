/**
 * @format
 */

import 'sariska-media-transport/modules/mobile/polyfills';
import {AppRegistry, StyleSheet, View} from 'react-native';
import React from "react"
import App from './App';
import {name as appName} from './app.json';



class BlueScreen extends React.Component {
    render() {
        return (
            <View style={styles.blue} />
        );
    }
}

class RedScreen extends React.Component {
    render() {
        return (
            <View style={styles.red} />
        );
    }
}

const styles = StyleSheet.create({
    blue: {
        backgroundColor: "#0000FF",
        width: "100%",
        height: "100%"
    },
    red: {
        backgroundColor: "#FF0000",
        width: "100%",
        height: "100%"
    }
});

AppRegistry.registerComponent('BlueScreen', () => BlueScreen);
AppRegistry.registerComponent('RedScreen', () => RedScreen);
AppRegistry.registerComponent(appName, () => App);
