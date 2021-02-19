/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */
import React, {useEffect} from 'react';
import {Provider} from 'react-redux';
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

const App = () => {
    useEffect(()=>{
        new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("NEW_MESSAGE", (data) => {

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
