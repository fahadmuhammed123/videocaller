import {
    ADD_LOCAL_TRACK,
    UPDATE_LOCAL_TRACK,
    REMOVE_LOCAL_TRACK,
    LOCAL_TRACK_ACTION
} from "../actions/types";

const initialState = [];

export const localTrack = (state = initialState, action) => {
        switch (action.type) {
            case ADD_LOCAL_TRACK:
                state.push(action.payload);
                return state.slice();
            case UPDATE_LOCAL_TRACK:
                index = state.findIndex(item => item.id === action.payload.id);
                state[index] = action.payload;
                return state.slice();
            case REMOVE_LOCAL_TRACK:
                state = state.filter(item => item.id !== action.payload.id);
                return state.slice();
            case LOCAL_TRACK_ACTION:
                const {param1, param2, method} = action;
                return state[method](param1, param2);
            default:
                return state;
        }
    }
;