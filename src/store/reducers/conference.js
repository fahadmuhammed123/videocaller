import {ADD_CONFERENCE, CONFERENCE_ACTION} from "../actions/types";

const initialState = null;

export const conference = (state = initialState, action) => {
    switch (action.type) {
        case ADD_CONFERENCE:
            state = action.payload;
            break;
        case "CONFERENCE":
            const {method} = action;
            state[method]();
            break;
        default:
            return state;
    }
}

