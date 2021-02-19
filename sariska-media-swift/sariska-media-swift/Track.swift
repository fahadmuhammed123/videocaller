
import Foundation

public class Track {
  
  public let type: String
  
  public let id: String

  public let remote: Bool
    
  public let deviceId: String
  
    init(id: String = "", type: String = "", remote: Bool = false) {
    self.id = id
    self.type = type
    self.remote = remote
  }
    
  public func attach() {
    let component: String = self.type == "video" ? "Video" : "Audio"
    return RNViewManager.sharedInstance.viewForModule(component, initialProperties: ["id": self.id])
  }
  
  public func mute() {
    BroadcastNativeEvent.shared?.sendEvent(withName: "TRACK",  body: ["mute"])
  }

  public func getType(): String-> {
        return self.type
  }
  
  public func unmute() {
    BroadcastNativeEvent.shared?.sendEvent(withName: "TRACK",  body: ["unmute"])
  }
  
  public func detach() {
        
  }
}
