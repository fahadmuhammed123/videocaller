import React from 'react';
import {Provider} from 'react-redux';
import {store} from './src/store/store';
import {NativeModules, NativeEventEmitter} from 'react-native';
import {connectionAction} from "./src/store/actions/connection";
import {conferenceAction} from "./src/store/actions/conference";
import {addLocalTrack} from "./src/store/actions/track";
import MessageQueue from 'react-native/Libraries/BatchedBridge/MessageQueue.js';
import * as actions from './src/store/actions/types';
import Conference from "./src/components/Conference";
import Connection from "./src/components/Connection";
import Video from "./src/components/Video";
import {initSDKConfig} from "./src/constants";
import SariskaNativeConnect from "./src/utils/SariskaNativeConnect";
import {extractTrackInfo} from "./src/utils";

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
            switch (action) {
                case actions.ADD_TRACK:
                    return store.dispatch(conferenceAction(type, action, store.localTrack.find(track => track.id === param1)));
                case actions.REMOVE_TRACK:
                    return store.dispatch(conferenceAction(type, action, store.localTrack.find(track => track.id === param1)));
                case actions.REPLACE_TRACK:
                    return store.dispatch(conferenceAction(type, action, store.localTrack.find(track => track.id === param1), store.localTrack.find(track => track.id === param2)));
            }
            return store.dispatch(conferenceAction(type, action, param1, param2));
        case actions.LOCAL_TRACK_ACTION:
            return store.dispatch(localTrackAction(type, action, param1, param2));
        case actions.REMOTE_TRACK_ACTION:
            return store.dispatch(remoteTrackAction(type, action, param1, param2));
        case actions.SARISKA_MEDIA_TRANSPORT_ACTION:
            if (param1.audio === true && param1.video === true && param1.desktop === true && action === actions.CREATE_LOCAL_TRACKS) {
                const videoTrack = await SariskaMediaTransport.createLocalTracks({
                    devices: ["video"],
                    resolution: options.resolution
                });
                const audioTrack = await SariskaMediaTransport.createLocalTracks({devices: ["audio"]});
                const desktopTrack = await SariskaMediaTransport.createLocalTracks({devices: ["desktop"]});
                store.dispatch(addLocalTrack(videoTrack));
                store.dispatch(addLocalTrack(audioTrack));
                store.dispatch(addLocalTrack(desktopTrack));
                return SariskaNativeConnect.newSariskaMediaTransportMessage("createLocalTracks", [extractTrackInfo(audioTrack), extractTrackInfo(videoTrack), extractTrackInfo(desktopTrack)]);
            }

            if (param1.audio === true && param1.video === true && action === actions.CREATE_LOCAL_TRACKS) {
                const videoTrack = await SariskaMediaTransport.createLocalTracks({
                    devices: ["video"],
                    resolution: options.resolution
                });
                const audioTrack = await SariskaMediaTransport.createLocalTracks({devices: ["audio"]});
                store.dispatch(addLocalTrack(videoTrack));
                store.dispatch(addLocalTrack(audioTrack));
                return SariskaNativeConnect.newSariskaMediaTransportMessage("createLocalTracks", [extractTrackInfo(audioTrack), extractTrackInfo(videoTrack)]);
            }

            if (param1.audio === true && action === actions.CREATE_LOCAL_TRACKS) {
                const audioTrack = await SariskaMediaTransport.createLocalTracks({devices: ["audio"]});
                store.dispatch(addLocalTrack(audioTrack));
                return SariskaNativeConnect.newSariskaMediaTransportMessage("createLocalTracks", [extractTrackInfo(audioTrack)]);
            }

            if (param1.video === true && action === actions.CREATE_LOCAL_TRACKS) {
                const videoTrack = await SariskaMediaTransport.createLocalTracks({devices: ["video"]});
                store.dispatch(addLocalTrack(videoTrack));
                return SariskaNativeConnect.newSariskaMediaTransportMessage("createLocalTracks", [extractTrackInfo(videoTrack)]);
            }

            if (param1.desktop === true && action === actions.CREATE_LOCAL_TRACKS) {
                const desktopTrack = await SariskaMediaTransport.createLocalTracks({devices: ["desktop"]});
                return SariskaNativeConnect.newSariskaMediaTransportMessage("createLocalTracks", [extractTrackInfo(desktopTrack)]);
            }

            if (action === actions.INIT_SDK) {
                return SariskaMediaTransport.init(param1 || initSDKConfig);
            }
    }
});


const App = () => {
    return (
        <Provider store={store}>
            <Connection/>
            <Conference/>
            <Video/>
        </Provider>
    )
};

export default App;
