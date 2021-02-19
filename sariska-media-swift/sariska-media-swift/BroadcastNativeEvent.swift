import Foundation
import React


@objc(BroadcastNativeEvent)
class BroadcastNativeEvent: RCTEventEmitter {

    public static var shared:BroadcastNativeEvent?

    override init() {
        super.init()
        BroadcastNativeEvent.shared = self
    }

    override func supportedEvents() -> [String]! {
        return []
    }
}
