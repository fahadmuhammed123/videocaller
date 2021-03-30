import {NativeModules, Platform} from 'react-native';

const sariskaConnectNative = NativeModules.SariskaConnectNative;

const SariskaConnectNative = {
    newConnectionMessage: msg => {
        sariskaConnectNative.newConnectionMessage(msg);
    },

    newConferenceMessage: msg => {
        sariskaConnectNative.newConferenceMessage(msg);
    },

    newLocalTrackMessage: msg => {
        sariskaConnectNative.newLocalTrackMessage(msg);
    },

    newRemoteTrackMessage: msg => {
        sariskaConnectNative.newRemoteTrackMessage(msg);
    },

    sendCallback: callback => {
        sariskaConnectNative.sendCallbackToNative(callback);
    }
};

export default SariskaConnectNative;
