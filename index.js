import 'sariska-media-transport/build/modules/mobile/polyfills';
import {AppRegistry, StyleSheet, View} from 'react-native';
import React from "react"
import App from './App';
import {name as appName} from './app.json';
import Conference from "./src/components/Conference";
import Connection from "./src/components/Connection";
import Video from "./src/components/Video";

AppRegistry.registerComponent(appName, () => App);
AppRegistry.registerComponent("Conference", ()=>Conference);
AppRegistry.registerComponent("Connection", ()=>Connection);
AppRegistry.registerComponent("Video", ()=>Video);
