import {ADD_CONFERENCE, CONFERENCE_ACTION} from "../actions/types";
const initialState = null;

export const conference = (state = initialState, action) => {
    switch (action.type) {
        case ADD_CONFERENCE:
            state = action.payload;
            return state;
        case CONFERENCE_ACTION:
            const {param1, param2, method} = action;
            return state[method](param1, param2);
        default:
            return state;
    }
}