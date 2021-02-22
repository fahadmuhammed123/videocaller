//
//  Connection.swift
//  sariska-media-swift
//  Created by brajendra  kumar on 16/02/21.
//

import Foundation

public class Conference: EventEmitter {
    
    public typealias Payload = [String: Any]
    
    let tracks:[Track] = []
    
    let users:[User] = []
    
    let localUser: User
   
    public func leave() {
        BroadcastNativeEvent.shared?.sendEvent(withName: "Conference",  body: ["leave"])
    }
    
    public func myUserId() -> String {
        return localUser.id
    }
    
    public func getLocalUser()->User {
        return localUser
    }
    
    public func sendTextMessage(text: String) {
        BroadcastNativeEvent.shared?.sendEvent(withName: "Conference",  body:["sendTextMessage"])
    }
    
    public func setDisplayName(name: String) {
        BroadcastNativeEvent.shared?.sendEvent(withName: "Conference",  body:["sendTextMessage"])
    }
    
    
    public func selectParticipant(id: String) {
        BroadcastNativeEvent.shared?.sendEvent(withName: "Conference",  body:["selectParticipant"])
    }
    
    public func addTrack(track: Track) {
        
    }
    
    public func removeTrack(track: Track) {
        
    }
    
    public func lock(password: String) {
        
    }
    
    public func unlock(){
        
    }
    
    public func kickParticipant(id: String) {
        
    }
    
    public init(params: Payload? = nil) {
        _ = RNViewManager.sharedInstance.viewForModule("Conference", initialProperties: params)
        
    }
}


extension Conference: ConferenceEventDelegate {
    
    func newConferenceMessage(type: String, value: String) {
        switch type {
            case "CONFERNECE_JOINED":
                self.localUser = value.user
                self.trigger(event: type)
                break
            case "TRACK_ADDED":
                let track = Track(value)
                tracks.append(track)
                self.trigger(event: type, track)
            case "TRACK_REMOVED":
                let track;
                if let i = tracks.firstIndex(where: { $0.id == value.id }) {
                    track =  array[i]
                }
                let foundItems = tracks.f { $0.id == value.id }
                tracks.append(track)
                self.trigger(event: type, track)
            case "USER_JOINED":
                self.users.append(User(value))
            case "USER_LEFT":
                let user = value
                let foundItems = users.filter { $0.id == value.id }
                self.users = foundItems
              default:
                self.trigger(event: type)
        }
    }
}
