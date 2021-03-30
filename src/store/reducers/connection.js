import {ADD_CONNECTION, CONNECTION_ACTION} from "../actions/types";

const initialState = null;

export const connection = (state = initialState, action) => {
    switch (action.type) {
        case ADD_CONNECTION:
            state = action.payload;
            return state;
        case CONNECTION_ACTION:
            return state[action.method]();;
        default:
            return state;
    }
}