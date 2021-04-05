import {
    ADD_LOCAL_TRACK,
    UPDATE_LOCAL_TRACK,
    REMOVE_LOCAL_TRACK,
    LOCAL_TRACK_ACTION, SWITCH_CAMERA
} from "../actions/types";

const initialState = [];

export const localTrack = (state = initialState, action) => {
    switch (action.type) {
        case SWITCH_CAMERA:
            const track = state.find(item=>item.id===action.payload.getId());
            track._switchCamera();
            return state;
        case ADD_LOCAL_TRACK:
            state.push(action.payload);
            return state;
        case UPDATE_LOCAL_TRACK:
            index = state.findIndex(item => item.id === action.payload.getId());
            state[index] = action.payload;
            return state;
        case REMOVE_LOCAL_TRACK:
            state = state.filter(item => item.id !== action.payload.getId());
            return state;
        case LOCAL_TRACK_ACTION:
            const {param1, param2, method} = action;
            state[method](param1, param2);
            return state;
        default:
            return state;
    }
};
