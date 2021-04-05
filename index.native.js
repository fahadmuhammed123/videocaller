import 'allsettled-polyfill';
import 'sariska-media-transport/build/modules/mobile/polyfills';
import {AppRegistry} from 'react-native';
import React from "react"
import {Provider} from "react-redux";
import {store} from "./src/store/store";
import App from "./App";
import Video from "./src/components/Video";
import {name as appName} from './app.json';

const VideoWithProvider = (initialProps) => <Provider store={store}><Video initialProps={initialProps}/></Provider>
AppRegistry.registerComponent(appName, () => App);
AppRegistry.registerComponent("Video", () => VideoWithProvider);
