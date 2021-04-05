import {SARISKA_MEDIA_TRANSPORT_ACTION} from "../actions/types";
import SariskaMediaTransport from "sariska-media-transport";
import {initSDKConfig} from "../../constants";
const initialState = SariskaMediaTransport;


export const sariskaMediaTransport = (state = initialState, action) => {
    switch (action.type) {
        case SARISKA_MEDIA_TRANSPORT_ACTION:
            const {method} = action;
            state[method](initSDKConfig);
            return state;
        default:
            return state;
    }
}
