import {NativeModules} from 'react-native';
const Conference = NativeModules.Conference;
const Connection = NativeModules.Connection;
const SariskaMediaTransport = NativeModules.SariskaMediaTransport;

const SariskaConnectNative = {
    newConnectionMessage: (type)  => {
        Connection.newConnectionMessage(type);
    },

    newConferenceMessage: (type)  => {
        Conference.newConferenceMessage(type);
    },

    newSariskaMediaTransportMessage: (type, msg) => {
        SariskaMediaTransport.newSariskaMediaTransportMessage(msg);
    },

    newLocalTrackMessage: (type, msg) => {
        SariskaMediaTransport.newLocalTrackMessage(type, msg);
    },

    newRemoteTrackMessage: (type, msg) => {
        Conference.newRemoteTrackMessage(type, msg);
    }
};

export default SariskaConnectNative;
