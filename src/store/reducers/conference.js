import {ADD_CONFERENCE, CONFERENCE_ACTION} from "../actions/types";
const initialState = null;

export const conference = (state = initialState, action) => {
    switch (action.type) {
        case ADD_CONFERENCE:
            state = action.payload;
            return state;
        case CONFERENCE_ACTION:
            return state[action.method]();
        default:
            return state;
    }
}