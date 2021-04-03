import {ADD_CONFERENCE, CONFERENCE_ACTION, REMOVE_CONFERENCE} from "../actions/types";
const initialState = null;

export const conference = (state = initialState, action) => {
    switch (action.type) {
        case ADD_CONFERENCE:
            state = action.payload;
            return state;
        case REMOVE_CONFERENCE:
            state = null;
            return state
        case CONFERENCE_ACTION:
            if (state === null) {
                return;
            }
            const {param1, param2, method} = action;
            console.log("param1...", param1, method);
            return state[method](param1, param2);
        default:
            return state;
    }
}
