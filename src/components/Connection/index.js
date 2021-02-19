import React, {useState, useEffect} from 'react';
import JitsiMeetJS from "sariska-media-transport";
import {connectionConfig, initSDKConfig} from "../../constants";
import {getToken} from "../../utils";
import NetInfo from "@react-native-community/netinfo";
import {SariskaNativeConnect} from "../../utils/SariskaNativeConnect";

const Connection = (options={}) => {
    const [connection, setConnection] = useState(null);

    useEffect(() => {
        JitsiMeetJS.init(initSDKConfig);
        JitsiMeetJS.setLogLevel(JitsiMeetJS.logLevels.ERROR); //TRACE ,DEBUG, INFO, LOG, WARN, ERROR
        let conn;

        const fetchData = async () => {
            if (!options.apiKey) {
                return;
            }

            const token = await getToken(options);
            if (!token) {
                return;
            }
            conn = new JitsiMeetJS.JitsiConnection(token, connectionConfig);
            conn.addEventListener(JitsiMeetJS.events.connection.CONNECTION_ESTABLISHED, onConnectionSuccess);
            conn.addEventListener(JitsiMeetJS.events.connection.CONNECTION_FAILED, onConnectionFailed);
            conn.addEventListener(JitsiMeetJS.events.connection.CONNECTION_DISCONNECTED, onConnectionDisconnected);
            conn.addEventListener(JitsiMeetJS.events.connection.PASSWORD_REQUIRED, onConnectionDisconnected);
            conn.connect();
        }

        const onConnectionSuccess = () => {
            setConnection(conn);
            SariskaNativeConnect.newConnectionMessage(JitsiMeetJS.events.connection.CONNECTION_ESTABLISHED);
        }

        const onConnectionDisconnected = (error) => {
            if (!connection) {
                return;
            }
            connection.removeEventListener(
                JitsiMeetJS.events.connection.CONNECTION_ESTABLISHED,
                onConnectionSuccess);
            connection.removeEventListener(
                JitsiMeetJS.events.connection.CONNECTION_FAILED,
                onConnectionFailed);
            connection.removeEventListener(
                JitsiMeetJS.events.connection.CONNECTION_DISCONNECTED,
                onConnectionDisconnected)
            SariskaNativeConnect.newConnectionMessage(JitsiMeetJS.events.connection.CONNECTION_DISCONNECTED);
        }

        const onConnectionFailed = async (error) => {
            if (error === JitsiMeetJS.errors.connection.PASSWORD_REQUIRED) {  // token expired,  fetch new token and set again
                SariskaNativeConnect.newConnectionMessage(JitsiMeetJS.errors.connection.PASSWORD_REQUIRED);
            }
        }

        const unsubscribe = NetInfo.addEventListener(state => {
            console.log("Is connected?", state.isConnected);
            JitsiMeetJS.setNetworkInfo({isOnline: state.isConnected});
        });

        fetchData();

        return () => {
            unsubscribe();
        };

    }, []);

    return null;
}

export default Connection;
