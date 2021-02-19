import { combineReducers } from "redux";
import { conference } from "./conference";
import { connection } from "./connection";
import { track } from "./track";

export const appReducer = combineReducers({
    conference,
    connection,
    track
});

export const rootReducer = (state, action) => {
    return appReducer(state, action);
}
