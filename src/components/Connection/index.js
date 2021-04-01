import React, {useEffect} from 'react';
import SariskaMediaTransport from "sariska-media-transport";
import {connectionConfig} from "../../constants";
import NetInfo from "@react-native-community/netinfo";
import {setConnection} from "../../store/actions/connection";
import {useDispatch} from "react-redux";
import SariskaNativeConnect from "../../utils/SariskaNativeConnect";

const Connection = ({token, options=connectionConfig}) => {
    const dispatch = useDispatch();
    useEffect(() => {
        if (!token) {
            return;
        }

        let connection;
        const onConnectionSuccess = () => {
            dispatch(setConnection(connection));
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

        connection = new SariskaMediaTransport.JitsiConnection(token, options);
        connection.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_ESTABLISHED, onConnectionSuccess);
        connection.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_FAILED, onConnectionFailed);
        connection.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_DISCONNECTED, onConnectionDisconnected);
        connection.connect();

        const unsubscribe = NetInfo.addEventListener(state => {
            console.log("Is connected?", state.isConnected);
            SariskaMediaTransport.setNetworkInfo({isOnline: state.isConnected});
        });

        return ()=>{
            unsubscribe();
        }
    }, [token]);

    return null;
}

export default Connection;
