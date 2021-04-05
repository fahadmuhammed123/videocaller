import React from 'react';
import {NativeModules, NativeEventEmitter} from "react-native";
import SariskaMediaTransport from "sariska-media-transport";
import {store} from './src/store/store';
import {createConference} from "./Conference";
import {createConnection} from "./Connection";
import {localTrackAction} from "./src/store/actions/track";
import {remoteTrackAction} from "./src/store/actions/track";
import {conferenceAction} from "./src/store/actions/conference";
import {addLocalTrack} from "./src/store/actions/track";
import * as actions from './src/store/actions/types';
import SariskaNativeConnect from "./src/utils/SariskaNativeConnect";
import {extractTrackInfo} from "./src/utils";
import {sariskaMediaTransportAction} from "./src/store/actions/sariska";

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("CREATE_CONNECTION", async (data) => {
    const {token} = data;
    const state = store.getState();
    if (!token || state.connection) {
        return;
    }
    return createConnection(token);
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("CREATE_CONFERENCE", async (data) => {
    const state = store.getState();
    if (state.conference || !state.connection) {
        return;
    }
    return createConference();
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("CONNECTION_ACTION", async (data) => {
    const {action, param1, param2} = data;
    store.dispatch(connectionAction("CONNECTION_ACTION", action, param1, param2));
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("LOCAL_TRACK_ACTION", async (data) => {
    const {action, param1, param2} = data;
    store.dispatch(localTrackAction("LOCAL_TRACK_ACTION", action, param1, param2));
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("SWITCH_CAMERA", async (data) => {
    const state = store.getState();
    const {action, param1} = data;
    store.dispatch(localTrackAction("SWITCH_CAMERA", action, state.localTrack.find(track => track.getId() === param1)));
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("REMOTE_TRACK_ACTION", async (data) => {
    const {action, param1, param2} = data;
    store.dispatch(remoteTrackAction("REMOTE_TRACK_ACTION", action, param1, param2));
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("CONFERENCE_ACTION", async (data) => {
    const state = store.getState();
    const {action, param1, param2} = data;
    switch (action) {
        case actions.ADD_TRACK:
            return store.dispatch(conferenceAction("CONFERENCE_ACTION", action, state.localTrack.find(track => track.getId() === param1)));
        case actions.REMOVE_TRACK:
            return store.dispatch(conferenceAction("CONFERENCE_ACTION", action, state.localTrack.find(track => track.getId() === param1)));
        case actions.REPLACE_TRACK:
            return store.dispatch(conferenceAction("CONFERENCE_ACTION", action, state.localTrack.find(track => track.getId() === param1), state.localTrack.find(track => track.getId() === param2)));
    }
    return store.dispatch(conferenceAction("CONFERENCE_ACTION", action, param1, param2));
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("CREATE_LOCAL_TRACKS", async (data) => {
    const {
        audio,
        video,
        desktop,
        resolution,
        cameraDeviceId,
        micDeviceId,
        minFps,
        maxFps,
        facingMode,
        desktopMinFps,
        desktopMaxFps,
        desktopSharingSourceDevice
    } = data;

    const audioOptions = {};

    if (audio === true) {
        audioOptions.devices = ["audio"];
    }

    if (micDeviceId) {
        audioOptions.micDeviceId = micDeviceId;
    }

    const videoOptions = {};

    if (video === true) {
        videoOptions.devices = ["video"];
    }

    if (resolution) {
        videoOptions.resolution = resolution;
    }

    if (cameraDeviceId) {
        videoOptions.cameraDeviceId = cameraDeviceId;
    }

    if (minFps) {
        videoOptions.minFps = minFps;
    }

    if (maxFps) {
        videoOptions.maxFps = maxFps;
    }

    if (facingMode) {
        videoOptions.facingMode = facingMode;
    }

    const desktopOptions = {};

    if (desktop === true) {
        desktopOptions.devices = ["desktop"];
    }

    if (desktopMinFps) {
        desktopOptions.desktopMinFps = desktopMinFps;
    }

    if (desktopMaxFps) {
        desktopOptions.desktopMaxFps = desktopMaxFps;
    }

    if (desktopSharingSourceDevice) {
        desktopOptions.desktopSharingSourceDevice = desktopSharingSourceDevice;
    }

    if (audio === true && video === true && desktop === true) {
        const videoTrack = await SariskaMediaTransport.createLocalTracks(videoOptions);
        const audioTrack = await SariskaMediaTransport.createLocalTracks(audioOptions);
        const desktopTrack = await SariskaMediaTransport.createLocalTracks(desktopOptions);
        store.dispatch(addLocalTrack(videoTrack[0]));
        store.dispatch(addLocalTrack(audioTrack[0]));
        store.dispatch(addLocalTrack(desktopTrack[0]));
        return SariskaNativeConnect.newLocalTrackMessage("CREATE_LOCAL_TRACKS", [extractTrackInfo(audioTrack[0]), extractTrackInfo(videoTrack[0]), extractTrackInfo(desktopTrack[0])]);
    }

    if (audio === true && video === true) {
        const videoTrack = await SariskaMediaTransport.createLocalTracks(videoOptions);
        const audioTrack = await SariskaMediaTransport.createLocalTracks(audioOptions);
        store.dispatch(addLocalTrack(videoTrack[0]));
        store.dispatch(addLocalTrack(audioTrack[0]));
        return SariskaNativeConnect.newLocalTrackMessage("CREATE_LOCAL_TRACKS", [extractTrackInfo(audioTrack[0]), extractTrackInfo(videoTrack[0])]);
    }

    if (audio === true) {
        const audioTrack = await SariskaMediaTransport.createLocalTracks(audioOptions);
        store.dispatch(addLocalTrack(audioTrack[0]));
        return SariskaNativeConnect.newLocalTrackMessage("CREATE_LOCAL_TRACKS", [extractTrackInfo(audioTrack[0])]);
    }

    if (video === true) {
        const videoTrack = await SariskaMediaTransport.createLocalTracks(videoOptions);
        store.dispatch(addLocalTrack(videoTrack[0]));
        return SariskaNativeConnect.newLocalTrackMessage("CREATE_LOCAL_TRACKS", [extractTrackInfo(videoTrack[0])]);
    }

    if (desktop === true) {
        const desktopTrack = await SariskaMediaTransport.createLocalTracks(desktopOptions);
        return SariskaNativeConnect.newLocalTrackMessage("CREATE_LOCAL_TRACKS", [extractTrackInfo(desktopTrack[0])]);
    }
});

new NativeEventEmitter(NativeModules.BroadcastNativeEvent).addListener("SARISKA_MEDIA_TRANSPORT_ACTION", async (data) => {
    const {action, param1, param2} = data;
    return store.dispatch(sariskaMediaTransportAction("SARISKA_MEDIA_TRANSPORT_ACTION", action, param1, param2));
});

export default App;
