import {ADD_CONNECTION, REMOVE_CONNECTION} from "./types";

export const addConnection = (connection) => {
    return {
        type: ADD_CONNECTION,
        payload: connection
    }
}

export const removeConnection = () => {
    return {
        type: REMOVE_CONNECTION
    }
}

export const connectionAction = (type, method, param1, param2) => {
    return {
        type,
        method
    }
}
