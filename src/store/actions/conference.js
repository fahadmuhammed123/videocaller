import {ADD_CONFERENCE, REMOVE_CONFERENCE} from "./types";

export const addConference = (conference) => {
    return {
        type: ADD_CONFERENCE,
        payload: conference
    }
}

export const removeConference = () => {
    return {
        type: REMOVE_CONFERENCE
    }
}

export const conferenceAction = (type, method, param1, param2) => {
    return {
        type,
        param1,
        param2,
        method
    }
}
