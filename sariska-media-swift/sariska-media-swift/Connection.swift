//
//  Connection.swift
//  sariska-media-swift
//  Created by brajendra  kumar on 16/02/21.
//

import Foundation

public class Connection: EventEmitter {
    
    public func disconnect() {
        
    }
    
    public init(params: Payload? = nil) {
        _ = RNViewManager.sharedInstance.viewForModule("Connection", initialProperties: params)
    }
}

extension Connection: ConnectionEventDelegate {
    
    func newConnectionMessage(type: String, value: String) {
        self.trigger(event: type)
    }
    
}
