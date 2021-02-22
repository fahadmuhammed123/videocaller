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
        new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("NEW_MESSAGE", (data) => {
            const {type, method} = data;
            switch (type) {
                case "CONNECTION":
                    return dispatch(connectionAction(type, method));
                case "CONFERENCE":
                    return dispatch(conferenceAction(type, method));
                case "TRACK":
                    return dispatch(trackAction(type, method, data.trackId));
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
