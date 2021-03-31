import React from "react";
import SariskaMediaTransport from "sariska-media-transport";
import {useDispatch, useSelector} from "react-redux";
import {SariskaNativeConnect} from "../../utils/SariskaNativeConnect";
import {initSDKConfig} from "../../constants";

const SariskaMediaTransport = (props) => {
    const action = useSelector(state=>state.action);
    const dispatch = useDispatch();

    useEffect(()=> {
        const options;
        if (action === "createLocalTracks" && options.video === true && options.audio === true) {
            const videoTrack = await SariskaMediaTransport.createLocalTracks({devices: ["video"], resolution: options.resolution});
            const audioTrack = await SariskaMediaTransport.createLocalTracks({devices: ["audio"]});
            SariskaNativeConnect.newSariskaMediaTransportMessage(SariskaMediaTransport.events.connection.CONNECTION_ESTABLISHED);
        } else if (action === "createLocalTracks" && options.video === true ) {

        } else if (action === "createLocalTracks" && options.video === true ) {

        } else if (action === "createLocalTracks" && options.desktop === true ) {

        }

        if (action === "init") {
            SariskaMediaTransport.init(initSDKConfig);
        }
    }, [props.options.action]);
    return null;
}

export default SariskaMediaTransport;
