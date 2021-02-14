import React from 'react';
import {FlatList} from 'react-native';
import Video from "../Video";
import Audio from "../Audio";

const RemoteStream = (props) => {
    const {remoteTracks} = props;

    const renderItemComponent = ({index, item}) => {
        return item.type ==="video" ? <Video track={item}/> : <Audio track={item}/>;
    }

    return (
        <FlatList
            data={remoteTracks}
            renderItem={item => renderItemComponent(item)}
            keyExtractor={item => item.track.id}
        />
    );
}

export default RemoteStream;
