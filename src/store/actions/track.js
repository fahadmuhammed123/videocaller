import {ADD_TRACK, ADD_TRACKS, REMOVE_TRACK, UPDATE_TRACK} from "./types";

export const addTrack = (track) => {
    return {
        type: ADD_TRACK,
        payload: track
    }
}

export const removeTrack = (track) => {
    return {
        type: REMOVE_TRACK,
        payload: track
    }
}


export const addTracks = (tracks) => {
    return {
        type: ADD_TRACKS,
        payload: tracks
    }
}

export const updateTrack = (track) => {
    return {
        type: UPDATE_TRACK,
        payload: track
    }
}

export const trackAction = (type, method, trackId) => {
    return {
        type,
        method,
        trackId
    }
}
