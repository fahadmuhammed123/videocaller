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

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("new_message", async (data) => {
    const {type, action, param1, param2} = data;
    switch (type) {
        case actions.CONNECTION_ACTION:
            return store.dispatch(connectionAction(type, action, param1, param2));
        case actions.CONFERENCE_ACTION:
            switch (method) {
                case actions.ADD_TRACK:
                    return store.dispatch(conferenceAction(type, action, store.localTrack.find(track=>track.id===param1)));
                case actions.REMOVE_TRACK:
                    return store.dispatch(conferenceAction(type, action, store.localTrack.find(track=>track.id===param1)));
                case actions.REPLACE_TRACK:
                    return store.dispatch(conferenceAction(type, action, store.localTrack.find(track=>track.id===param1), store.localTrack.find(track=>track.id===param2)));
            }
            return store.dispatch(conferenceAction(type, action, param1, param2));
        case actions.LOCAL_TRACK_ACTION:
            return store.dispatch(localTrackAction(type, action, param1, param2));
        case actions.REMOTE_TRACK_ACTION:
            return store.dispatch(remoteTrackAction(type, action, param1, param2));
        case actions.SARISKA_MEDIA_TRANSPORT_ACTION:
            switch (method) {
                if (  param1.audio === true &&  param1.video === true && method === actions.INIT_SDK ) {
                    const videoTrack = await SariskaMediaTransport.createLocalTracks({devices: ["video"], resolution: options.resolution});
                    const audioTrack = await SariskaMediaTransport.createLocalTracks({devices: ["audio"]});
                    return store.dispatch(sariskaMediaTransportAction(type, action, param1, param2))
                }

                if (param1.audio === true && method === actions.CREATE_LOCAL_TRACKS) {
                     return store.dispatch(sariskaMediaTransportAction(type, action, param1, param2));
                }

                if (param1.audio === true &&  param1.video === true && method === actions.CREATE_LOCAL_TRACKS) {
                     return store.dispatch(sariskaMediaTransportAction(type, action, param1, param2));
                }

                if (param1.desktop === true &&  param1.video === true && method === actions.CREATE_LOCAL_TRACKS) {
                    return store.dispatch(sariskaMediaTransportAction(type, action, param1, param2));
                }

                if (param1.desktop === true &&  param1.video === true && method === actions.INIT_SDK) {
                     return store.dispatch(sariskaMediaTransportAction(type, action, param1, param2));
                }
            }
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