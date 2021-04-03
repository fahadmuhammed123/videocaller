import SariskaMediaTransport from "sariska-media-transport";
import NetInfo from "@react-native-community/netinfo";
import {addConnection} from "./src/store/actions/connection";
import SariskaNativeConnect from "./src/utils/SariskaNativeConnect";
import {connectionConfig} from "./src/constants";
import {store} from './src/store/store';

export function createConnection(token) {
    const connection = new SariskaMediaTransport.JitsiConnection(token, connectionConfig);
    const onConnectionSuccess = () => {
        store.dispatch(addConnection(connection));
        SariskaNativeConnect.newConferenceMessage(SariskaMediaTransport.events.connection.CONNECTION_ESTABLISHED);
    }

    const onConnectionFailed = async (error) => {
        SariskaNativeConnect.newConferenceMessage(SariskaMediaTransport.events.connection.CONNECTION_FAILED);
    }

    const onConnectionDisconnected = (error) => {
        if (!connection) {
            return;
        }
        connection.removeEventListener(
            SariskaMediaTransport.events.connection.CONNECTION_ESTABLISHED,
            onConnectionSuccess);
        connection.removeEventListener(
            SariskaMediaTransport.events.connection.CONNECTION_FAILED,
            onConnectionFailed);
        connection.removeEventListener(
            SariskaMediaTransport.events.connection.CONNECTION_DISCONNECTED,
            onConnectionDisconnected);
        SariskaNativeConnect.newConnectionMessage(SariskaMediaTransport.events.connection.CONNECTION_DISCONNECTED);
    }

    connection.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_ESTABLISHED, onConnectionSuccess);
    connection.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_FAILED, onConnectionFailed);
    connection.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_DISCONNECTED, onConnectionDisconnected);
    connection.connect();

    const unsubscribe = NetInfo.addEventListener(state => {
        console.log("Is connected?", state.isConnected);
        SariskaMediaTransport.setNetworkInfo({isOnline: state.isConnected});
    });
}


