import * as Constants from "./types";

export const addLocalTrack = (track) => {
    return {
        type: Constants.ADD_LOCAL_TRACK,
        payload: track
    }
}

export const removeLocalTrack = (track) => {
    return {
        type: Constants.REMOVE_LOCAL_TRACK,
        payload: track
    }
}

export const updateLocalTrack = (track) => {
    return {
        type: Constants.UPDATE_LOCAL_TRACK,
        payload: track
    }
}

export const addRemoteTrack = (track) => {
    return {
        type: Constants.ADD_REMOTE_TRACK,
        payload: track
    }
}

export const removeRemoteTrack = (track) => {
    return {
        type: Constants.REMOVE_REMOTE_TRACK,
        payload: track
    }
}

export const updateRemoteTrack = (track) => {
    return {
        type: Constants.UPDATE_REMOTE_TRACK,
        payload: track
    }
}


export const localTrackAction = (type, method, param1, param2) => {
    return {
        type,
        method,
        param1,
        param2
    }
}


export const remoteTrackAction = (type, method, param1, param2) => {
    return {
        type,
        method,
        param1,
        param2
    }
}
