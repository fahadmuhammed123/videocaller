import { combineReducers } from "redux";
import { conference } from "./conference";
import { connection } from "./connection";
import { localTrack } from "./localTrack";
import { remoteTrack } from "./remoteTrack";
import { sariskaMediaTransport } from "./sariskaMediaTransport";

export const appReducer = combineReducers({
    conference,
    connection,
    localTrack,
    remoteTrack,
    sariskaMediaTransport
});

export const rootReducer = (state, action) => {
    return appReducer(state, action);
}
