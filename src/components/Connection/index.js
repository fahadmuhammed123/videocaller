import React, {useState, useEffect} from 'react';
import SariskaMediaTransport from "sariska-media-transport";
import {connectionConfig, initSDKConfig} from "../../constants";
import {getToken} from "../../utils";
import NetInfo from "@react-native-community/netinfo";
import Conference from "../Conference";

const Connection = (props) => {

    const [connection, setConnection] = useState(null);

    useEffect(() => {
        SariskaMediaTransport.init(initSDKConfig);
        SariskaMediaTransport.setLogLevel(SariskaMediaTransport.logLevels.ERROR); //TRACE ,DEBUG, INFO, LOG, WARN, ERROR
        let conn;

        const fetchData = async () => {
            const token = await getToken();
            if (!token) {
                return;
            }
            conn = new SariskaMediaTransport.JitsiConnection(token, connectionConfig);
            conn.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_ESTABLISHED, onConnectionSuccess);
            conn.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_FAILED, onConnectionFailed);
            conn.addEventListener(SariskaMediaTransport.events.connection.CONNECTION_DISCONNECTED, onConnectionDisconnected);
            conn.addEventListener(SariskaMediaTransport.events.connection.PASSWORD_REQUIRED, onConnectionDisconnected);
            conn.connect();
        }

        const onConnectionSuccess = () => {
            setConnection(conn);
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
                onConnectionDisconnected)
        }

        const onConnectionFailed = async (error) => {
            if (error === SariskaMediaTransport.errors.connection.PASSWORD_REQUIRED) {  // token expired,  fetch new token and set again
                const token = await getToken();
                conn.setToken(token);
            }
        }

        const unsubscribe = NetInfo.addEventListener(state => {
            console.log("Is connected?", state.isConnected);
            SariskaMediaTransport.setNetworkInfo({isOnline: state.isConnected});
        });

        fetchData();

        return () => {
            unsubscribe();
        };

    }, []);

    return (<Conference connection={connection}/>);
}

export default Connection;
