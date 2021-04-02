import {NativeModules} from 'react-native';
const Conference = NativeModules.Conference;
const Connection = NativeModules.Connection;
const SariskaMediaTransport = NativeModules.SariskaMediaTransport;

const SariskaConnectNative = {
    newConnectionMessage: (type)  => {
        Connection.newConnectionMessage(type);
    },

    newConferenceMessage: (type)  => {
        Conference.newConferenceMessage(type, param1, param2, param3, param4);
    },

    newSariskaMediaTransportMessage: (type, msg) => {
        SariskaMediaTransport.newSariskaMediaTransportMessage(msg);
    },

    newLocalTrackMessage: (type, msg) => {
        SariskaMediaTransport.newLocalTrackMessage(type, msg);
    },

    newRemoteTrackMessage: (type, msg) => {
        Conference.newLocalTrackMessage(type, msg);
    }
};

export default SariskaConnectNative;
