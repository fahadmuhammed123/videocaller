import React from 'react';
import Video from "../Video";
import Audio from "../Audio";
import {FlatList} from 'react-native';

const LocalStream = (props) => {
    const {localTracks} = props;

    const renderItemComponent = ({index, item}) => {
        return item.type ==="video" ? <Video track={item}/> : null;
    }

    return (
        <FlatList
            data={localTracks}
            renderItem={item => renderItemComponent(item)}
            keyExtractor={item => item.track.id}
        />
    );
}

export default LocalStream;
