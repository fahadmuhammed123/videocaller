import Foundation
import React


protocol ConnectionEventDelegate: class {
    func newConnectionMessage(type: String, value: String) -> Void
}

protocol ConferenceEventDelegate: class {
    func newConferenceMessage(type: String, value: String) -> Void
}

protocol LocalTrackEventDelegate: class {
    func newLocalTrackMessage(type: String, value: String) -> Void
}

@objc(SariskaConnectNativeModule)
class SariskaConnectNativeModule: NSObject {
    weak var delegateConnection: ConnectionEventDelegate?
    weak var delegateConference: ConferenceEventDelegate?
    weak var delegateLocalTrack: LocalTrackEventDelegate?

    @objc
    static func requiresMainQueueSetup() -> Bool {
        return true
    }

    @objc
    func newConnectionMessage(type: String, value: String) {
        print("This log is from swift: \(value)")
        self.delegateConnection?.newConnectionMessage(type: type, value: value)
    }

    @objc
    func newConferenceMessage(type: String, value: String) {
        print("This log is from swift: \(value)")
        self.delegateConference?.newConferenceMessage(type: type, value: value)

    }

    @objc
    func newTrackMessage(type: String, value: String) {
        print("This log is from swift: \(value)")
        self.delegateLocalTrack?.newLocalTrackMessage(type: type, value: value)
        
    }

    @objc
    func sendCallbackToNative(_ rnCallback: RCTResponseSenderBlock) {
        rnCallback(["A greeting from swift"])
    }

}
