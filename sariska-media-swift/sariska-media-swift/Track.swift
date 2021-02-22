
import Foundation

public class Track {

  public let type: String

  public let id: String

  public let remote: Bool

  public let deviceId: String
  
  public let muted: String
  
  public let participantId: String
    
    init(id: String = "", type: String = "", remote: Bool = false, deviceId: String = "", muted: String = "", participantId: String = "") {
     self.id = id
     self.type = type
     self.remote = remote
     self.deviceId = deviceId
     self.muted = muted
     self.participantId = participantId
  }

  public func attach()->NSObject {
    let component: String = self.type == "video" ? "Video" : "Audio"
    return RNViewManager.sharedInstance.viewForModule(component, initialProperties: ["id": self.id])
  }
 
  public func getDeviceId()->String {
     return self.deviceId;
  }
  
  public func mute() {
    BroadcastNativeEvent.shared?.sendEvent(withName: "TRACK",  body: ["mute", self.id])
  }

  public func getType()-> String {
      return self.type
  }
  
  public func getParticipantId()->String {
    return self.participantId
  }
  
  public func unmute() {
    BroadcastNativeEvent.shared?.sendEvent(withName: "TRACK",  body: ["unmute", self.id])
  }

  public func detach() {
    
  }
    
  public func dispose() {
    BroadcastNativeEvent.shared?.sendEvent(withName: "TRACK",  body: ["dispose", self.id])
  }
    
}
