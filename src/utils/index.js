export function extractTrackInfo(track) {
    return  {
        type: track.getType(),
        participantId: track.getParticipantId(),
        id: track.getId(),
        muted: track.isMuted(),
        deviceId: track.getDeviceId(),
        streamURL: track.stream?.toURL()
    };
}

export function extractRemoteTrackInfo(track) {
    return  {
        type: track.getType(),
        participantId: track.getParticipantId(),
        id: track.getId(),
        muted: track.isMuted(),
        streamURL: track.stream?.toURL()
    };
}
