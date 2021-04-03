import {SARISKA_MEDIA_TRANSPORT_ACTION} from "../actions/types";
import {initSDKConfig} from "../../constants";
const initialState = null;

export const sariskaMediaTransport = (state = initialState, action) => {
    switch (action.type) {
        case SARISKA_MEDIA_TRANSPORT_ACTION:
            const {method} = action;
            return state[method](initSDKConfig);
        default:
            return state;
    }
}
