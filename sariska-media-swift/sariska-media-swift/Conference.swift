//
//  Connection.swift
//  sariska-media-swift
//  Created by brajendra  kumar on 16/02/21.
//

import Foundation


public class Conference: EventEmitter {
    public typealias Payload = [String: Any]
    
    public func leave() {
        
    }
    
    public func myUserId() {
        
    }
    
    public func sendTextMessage(text: String) {
        
    }
    
    public func setDisplayName(name: String) {
        
    }
    
    
    public func selectParticipant(id: String) {
        
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
        self.trigger(event: type)
    }
    
}
