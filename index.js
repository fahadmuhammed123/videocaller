/**
 * @format
 */
import 'sariska-media-transport/dist/esm/modules/mobile/polyfills';
import {AppRegistry} from 'react-native';
import React from "react"
import App from './App';
import {name as appName} from './app.json';

AppRegistry.registerComponent(appName, () => App);
