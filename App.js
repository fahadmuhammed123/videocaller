/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */
import React, {useEffect} from 'react';
import {Provider, useDispatch} from 'react-redux';
import {store} from './src/store/store';
import {NativeModules,  NativeEventEmitter} from 'react-native';
import {connectionAction} from "./src/store/actions/connection";
import {conferenceAction} from "./src/store/actions/conference";
import {localTrackAction} from "./src/store/actions/localTrack";
import {remoteTrackAction} from "./src/store/actions/remoteTrack";
import MessageQueue from 'react-native/Libraries/BatchedBridge/MessageQueue.js';
import * as actions from './src/store/actions/types';
import Conference from "./src/components/Conference";
import SariskaMediaTransport from "./src/components/SariskaMediaTransport";
import Connection from "./src/components/Connection";
import Video from "./src/components/Video";

const spyFunction = (msg) => {
  console.log(msg);
};

MessageQueue.spy(spyFunction);

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("new_message", (data) => {
    const {type, action, options} = data;
    switch (type) {
        case actions.CONNECTION_ACTION:
            return store.dispatch(connectionAction(type, action));
        case actions.CONFERENCE_ACTION:
            return store.dispatch(conferenceAction(type, action));
        case actions.LOCAL_TRACK_ACTION:
            return store.dispatch(localTrackAction(type, action, options.trackId));
        case actions.REMOTE_TRACK_ACTION:
            return store.dispatch(remoteTrackAction(type, action, options.trackId));
    }
});


const App = () => {
    return (
        <Provider store={store}>
            <Connection/>
            <Conference/>
            <SariskaMediaTransport/>
            <Video/>
        </Provider>
    )
};

export default App;
