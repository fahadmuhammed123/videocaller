import React from 'react';
import {NativeModules, NativeEventEmitter} from "react-native";
import {store} from './src/store/store';
import {createConference} from "./Conference";
import {createConnection} from "./Connection";
import {conferenceAction} from "./src/store/actions/conference";
import {addLocalTrack} from "./src/store/actions/track";
//import MessageQueue from 'react-native/Libraries/BatchedBridge/MessageQueue.js';
import * as actions from './src/store/actions/types';
import SariskaNativeConnect from "./src/utils/SariskaNativeConnect";
import {extractTrackInfo} from "./src/utils";
import {sariskaMediaTransportAction} from "./src/store/actions/sariska";
//
// const spyFunction = (msg) => {
//     console.log("msgmsgmsg", msg);
// };

//MessageQueue.spy(spyFunction);

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("CREATE_CONNECTION", async (data) => {
    const {token} = data;
    if (!token || store.connection) {
        return;
    }
    return createConnection(token);
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("CREATE_CONFERENCE", async (data) => {
    if (store.conference || !store.connection) {
        return;
    }
    return createConference();
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("CONNECTION_ACTION", async (data) => {
    const {action, param1, param2} = data;
    store.dispatch(connectionAction("CONNECTION_ACTION", action, param1, param2));
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("CONFERENCE_ACTION", async (data) => {
    const {action, param1, param2} = data;
    switch (action) {
        case actions.ADD_TRACK:
            return store.dispatch(conferenceAction("CONFERENCE_ACTION", action, store.localTrack.find(track => track.id === param1)));
        case actions.REMOVE_TRACK:
            return store.dispatch(conferenceAction("CONFERENCE_ACTION", action, store.localTrack.find(track => track.id === param1)));
        case actions.REPLACE_TRACK:
            return store.dispatch(conferenceAction("CONFERENCE_ACTION", action, store.localTrack.find(track => track.id === param1), store.localTrack.find(track => track.id === param2)));
    }
    return store.dispatch(conferenceAction("CONFERENCE_ACTION", action, param1, param2));
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("CREATE_LOCAL_TRACKS", async (data) => {
    const {audio, video, desktop, resolution } = data;
    if (audio === true && video === true && desktop === true) {
        const videoTrack = await SariskaMediaTransport.createLocalTracks({
            devices: ["video"],
            resolution: resolution
        });
        const audioTrack = await SariskaMediaTransport.createLocalTracks({devices: ["audio"]});
        const desktopTrack = await SariskaMediaTransport.createLocalTracks({devices: ["desktop"]});
        store.dispatch(addLocalTrack(videoTrack));
        store.dispatch(addLocalTrack(audioTrack));
        store.dispatch(addLocalTrack(desktopTrack));
        return SariskaNativeConnect.newLocalTrackMessage("createLocalTracks", [extractTrackInfo(audioTrack), extractTrackInfo(videoTrack), extractTrackInfo(desktopTrack)]);
    }

    if (audio === true && video === true) {
        const videoTrack = await SariskaMediaTransport.createLocalTracks({
            devices: ["video"],
            resolution: resolution
        });
        const audioTrack = await SariskaMediaTransport.createLocalTracks({devices: ["audio"]});
        store.dispatch(addLocalTrack(videoTrack));
        store.dispatch(addLocalTrack(audioTrack));
        return SariskaNativeConnect.newLocalTrackMessage("createLocalTracks", [extractTrackInfo(audioTrack), extractTrackInfo(videoTrack)]);
    }

    if (audio === true) {
        const audioTrack = await SariskaMediaTransport.createLocalTracks({devices: ["audio"]});
        store.dispatch(addLocalTrack(audioTrack));
        return SariskaNativeConnect.newLocalTrackMessage("createLocalTracks", [extractTrackInfo(audioTrack)]);
    }

    if (video === true) {
        const videoTrack = await SariskaMediaTransport.createLocalTracks({devices: ["video"], resolution: resolution});
        store.dispatch(addLocalTrack(videoTrack));
        return SariskaNativeConnect.newLocalTrackMessage("createLocalTracks", [extractTrackInfo(videoTrack)]);
    }

    if (desktop === true) {
        const desktopTrack = await SariskaMediaTransport.createLocalTracks({devices: ["desktop"]});
        return SariskaNativeConnect.newLocalTrackMessage("createLocalTracks", [extractTrackInfo(desktopTrack)]);
    }
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("SARISKA_MEDIA_TRANSPORT_ACTION", async (data) => {
    const {action, param1, param2} = data;
    return store.dispatch(sariskaMediaTransportAction("SARISKA_MEDIA_TRANSPORT_ACTION", action, param1, param2));
});

const App = () => {
    return null;
};

export default App;
