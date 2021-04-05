import {ADD_CONNECTION, CONNECTION_ACTION, REMOVE_CONNECTION} from "../actions/types";

const initialState = null;

export const connection = (state = initialState, action) => {
    switch (action.type) {
        case ADD_CONNECTION:
            state = action.payload;
            return state;
        case REMOVE_CONNECTION:
            state = null;
            return state;
        case CONNECTION_ACTION:
            if (state === null) {
                return;
            }
            const {param1, param2, method} = action;
            state[method](param1, param2);
            return state;
        default:
            return state;
    }
}
