import {ADD_CONNECTION, CONNECTION_ACTION} from "../actions/types";

const initialState = null;

export const connection = (state = initialState, action) => {
    switch (action.type) {
        case ADD_CONNECTION:
            state = action.payload;
            break;
        case "CONNECTION":
            const {method} = action;
            state[method]();
            break;
        default:
            return state;
    }
}

