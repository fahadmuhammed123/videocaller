export function extractTrackInfo(track) {
    return  {
        type: track.getType(),
        participantId: track.getParticipantId(),
        id: track.getDeviceId(),
        muted: track.isMuted(),
    };
}
