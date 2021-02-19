
import Foundation

public class Message {
  
  public let ref: String
  
  public let event: String

  public var payload: Payload

  init(ref: String = "", event: String = "", payload: Payload = [:]) {
    self.ref = ref
    self.event = event
    self.payload = payload
  }
}
