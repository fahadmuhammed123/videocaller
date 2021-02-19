import {ADD_CONFERENCE} from "./types";

export const addConference = (conference) => {
    return {
        type: ADD_CONFERENCE,
        payload: conference
    }
}
