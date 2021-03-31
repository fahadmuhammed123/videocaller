import {ADD_REMOTE_TRACK, UPDATE_REMOTE_TRACK, REMOVE_REMOTE_TRACK, REMOTE_TRACK_ACTION} from "../actions/types";

const initialState = [];

export const remoteTrack = (state = initialState, action) => {
    switch (action.type) {
        case ADD_REMOTE_TRACK:
            state.push(action.payload);
            return state.slice();
        case UPDATE_REMOTE_TRACK:
            index = state.findIndex(item => item.id === action.payload.id);
            state[index] = action.payload;
            return state.slice();
        case REMOVE_REMOTE_TRACK:
            state = state.filter(item => item.id !== action.payload.id);
            return state.slice();
        case REMOTE_TRACK_ACTION:
            const {param1, param2, method} = action;
            return state[method](param1, param2);
        default:
            return state;
    }
}