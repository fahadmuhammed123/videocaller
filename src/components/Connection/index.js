import React, {useEffect} from 'react';
import SariskaMediaTransport from "sariska-media-transport";
import NetInfo from "@react-native-community/netinfo";
import {addConnection} from "../../store/actions/connection";
import {useDispatch, useSelector} from "react-redux";
import SariskaNativeConnect from "../../utils/SariskaNativeConnect";
import {connectionConfig} from "../../constants";

const Connection = (props) => {
    const dispatch = useDispatch();
    const connection = useSelector(state=>state.connection);
    const {token} = props?.initialProps;

    useEffect(() => {
        if ( !token || connection ) {
            return;
        }

        let conn;
        const onConnectionSuccess = () => {
            dispatch(addConnection(conn));
            SariskaNativeConnect.newConferenceMessage(SariskaMediaTransport.events.connection.CONNECTION_ESTABLISHED);
        }

        const onConnectionFailed = async (error) => {
            SariskaNativeConnect.newConferenceMessage(SariskaMediaTransport.events.connection.CONNECTION_FAILED);
        }

        const onConnectionDisconnected = (error) => {
            if (!conn) {
                return;
            }
            conn.removeEventListener(
                SariskaMediaTransport.events.connection.CONNECTION_ESTABLISHED,
                onConnectionSuccess);
            conn.removeEventListener(
                SariskaMediaTransport.events.connection.CONNECTION_FAILED,
                onConnectionFailed);
            conn.removeEventListener(
                SariskaMediaTransport.events.connection.CONNECTION_DISCONNECTED,
                onConnectionDisconnected);
            SariskaNativeConnect.newConnectionMessage(SariskaMediaTransport.events.connection.CONNECTION_DISCONNECTED);
        }

        conn = new SariskaMediaTransport.JitsiConnection(token, connectionConfig);
        conn.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_ESTABLISHED, onConnectionSuccess);
        conn.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_FAILED, onConnectionFailed);
        conn.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_DISCONNECTED, onConnectionDisconnected);
        conn.connect();

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
