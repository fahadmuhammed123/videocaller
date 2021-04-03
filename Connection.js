import SariskaMediaTransport from "sariska-media-transport";
import NetInfo from "@react-native-community/netinfo";
import {addConnection, removeConnection} from "./src/store/actions/connection";
import SariskaNativeConnect from "./src/utils/SariskaNativeConnect";
import {connectionConfig} from "./src/constants";
import {store} from './src/store/store';
import * as types from  './src/store/actions/types';

export function createConnection(token) {
    let connection;
    const onConnectionSuccess = () => {
        store.dispatch(addConnection(connection));
        SariskaNativeConnect.newConnectionMessage(types.CONNECTION_ESTABLISHED);
    }

    const onConnectionFailed = async (error) => {
        SariskaNativeConnect.newConnectionMessage(types.CONNECTION_FAILED);
    }

    const onConnectionDisconnected = (error) => {
        if (!connection) {
            return;
        }
        store.dispatch(removeConnection(connection));
        connection.removeEventListener(
            SariskaMediaTransport.events.connection.CONNECTION_ESTABLISHED,
            onConnectionSuccess);
        connection.removeEventListener(
            SariskaMediaTransport.events.connection.CONNECTION_FAILED,
            onConnectionFailed);
        connection.removeEventListener(
            SariskaMediaTransport.events.connection.CONNECTION_DISCONNECTED,
            onConnectionDisconnected);
        SariskaNativeConnect.newConnectionMessage(types.CONNECTION_DISCONNECTED);
    }
    connection = new SariskaMediaTransport.JitsiConnection(token, connectionConfig);

    connection.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_ESTABLISHED, onConnectionSuccess);
    connection.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_FAILED, onConnectionFailed);
    connection.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_DISCONNECTED, onConnectionDisconnected);
    connection.connect();

    const unsubscribe = NetInfo.addEventListener(state => {
        console.log("Is connected?", state.isConnected);
        SariskaMediaTransport.setNetworkInfo({isOnline: state.isConnected});
    });
}


