import {ADD_CONNECTION, CONNECTION_ACTION} from "../actions/types";

const initialState = null;

export const connection = (state = initialState, action) => {
    switch (action.type) {
        case ADD_CONNECTION:
            state = action.payload;
            return state;
        case CONNECTION_ACTION:
            const {param1, param2, method} = action;
            return state[method](param1, param2);
        default:
            return state;
    }
}