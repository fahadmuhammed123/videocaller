import {NativeModules} from 'react-native';
const sariskaConnectNative = NativeModules.SariskaConnectNative;

const SariskaConnectNative = {
    newConnectionMessage: (type, msg)  => {
        sariskaConnectNative.newConnectionMessage(type, msg);
    },

    newConferenceMessage: (type, msg)  => {
        sariskaConnectNative.newConferenceMessage(type, msg);
    },

    newSariskaMediaTransportMessage: (type, msg) => {
        sariskaConnectNative.newLocalTrackMessage(msg);
    },

    newLocalTrackMessage: (type, msg) => {
        sariskaConnectNative.newLocalTrackMessage(type, msg);
    },

    newRemoteTrackMessage: (type, msg) => {
        sariskaConnectNative.newLocalTrackMessage(type, msg);
    }
};

export default SariskaConnectNative;
