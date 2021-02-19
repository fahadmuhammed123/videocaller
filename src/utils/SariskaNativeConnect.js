// If TestConnectNative is an package from npm, you can think this is index.js file
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
    sendCallback: callback => {
        sariskaConnectNative.sendCallbackToNative(callback);
    }
};

export default SariskaConnectNative;
