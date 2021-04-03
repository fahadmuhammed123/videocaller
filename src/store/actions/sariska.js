import {ADD_CONNECTION} from "./types";

export const addConnection = (connection) => {
    return {
        type: ADD_CONNECTION,
        payload: connection
    }
}

export const connectionAction = (type, method) => {
    return {
        type,
        method
    }
}

export const sariskaMediaTransportAction = (type, method, param1, param2)=> {
    return {
        type,
        method,
        param1,
        param2
    }
}
