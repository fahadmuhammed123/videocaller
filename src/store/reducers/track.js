import {ADD_TRACKS, ADD_TRACK, UPDATE_TRACK, REMOVE_TRACK, TRACK_ACTION } from "../actions/types";

const initialState = [];

export const track =  (state = [], action) => {
    switch (action.type) {
        case ADD_TRACKS:
            state = action.payload;
            return state.slice();
        case ADD_TRACK:
            state.push(action.payload);
            return state.slice();
        case UPDATE_TRACK:
            index = state.findIndex(item => item.id === action.payload.id);
            state[index] = action.payload;
            return state.slice();
        case REMOVE_TRACK:
            state = state.filter(item => item.id !== action.payload.id);
            break;
        case "TRACK":
            const {method, trackId} = action;
            const track = state.track.find(item=>item.track.id===trackId);
            track[method]();
            break;
        default:
            return state;
    }
}
;