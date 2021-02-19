//
//  SariskaMediaTransport.swift
//  sariska-media-swift
//
//  Created by brajendra  kumar on 16/02/21.
//

import Foundation

public class SariskaMediaTransport: EventEmitter {
    
    public static func connection() {
        
    }
    
    public static func createLocalTracks() {
        
        
    }
    
}


extension SariskaMediaTransport: LocalTrackEventDelegate {
    
    func newLocalTrackMessage(type: String, value: String) {
        self.trigger(event: type)
    }
    
}
