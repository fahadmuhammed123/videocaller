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
import Conference from "./src/components/Conference";
import SariskaMediaTransport from "./src/components/SariskaMediaTransport";
import Connection from "./src/components/Connection";
import Audio from "./src/components/Audio";
import Video from "./src/components/Video";
import {
    NativeModules,
    NativeEventEmitter,
} from 'react-native';
import {conferenceAction} from "./src/store/actions/conference";
import {connectionAction} from "./src/store/actions/connection";
import {trackAction} from "./src/store/actions/track";

const App = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("new_message", (data) => {
            const {type, action} = data;
            switch (type) {
                case "CONNECTION":
                    return dispatch(connectionAction(type, action));
                case "CONFERENCE":
                    return dispatch(conferenceAction(type, action));
                case "TRACK":
                    return dispatch(trackAction(type, action, data.trackId));
            }
        });
    }, []);

    return (
        <Provider store={store}>
            <Connection/>
            <Conference/>
            <SariskaMediaTransport/>
            <Video/>
            <Audio/>
        </Provider>
    )
};

export default App;
