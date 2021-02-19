
import Swift

import Foundation

/// Container class of bindings to the channel
struct Binding {
  
  // The event that the Binding is bound to
  let event: String
  
  // The reference number of the Binding
  let ref: Int
  
  // The callback to be triggered
  let callback: Delegated<Message, Void>
}

public typealias Payload = [String: Any]


public class EventEmitter {
    
    var bindingsDel: [Binding]
    
    var bindingRef: Int
    
    init() {
        self.bindingsDel = []
        self.bindingRef = 0
    }
    
    @discardableResult
    public func on(_ event: String, callback: @escaping ((Message) -> Void)) -> Int {
      var delegated = Delegated<Message, Void>()
      delegated.manuallyDelegate(with: callback)
      
      return self.on(event, delegated: delegated)
    }
    
    @discardableResult
    private func on(_ event: String, delegated: Delegated<Message, Void>) -> Int {
      let ref = bindingRef
      self.bindingRef = ref + 1
      
      self.bindingsDel.append(Binding(event: event, ref: ref, callback: delegated))
      return ref
    }
    
    
    public func off(_ event: String, ref: Int? = nil) {
      self.bindingsDel.removeAll { (bind) -> Bool in
        bind.event == event && (ref == nil || ref == bind.ref)
      }
    }
    
    public var onMessage: (_ message: Message) -> Message = { (message) in
       return message
    }
    
    public func onMessage(callback: @escaping (Message) -> Message) {
      self.onMessage = callback
    }
    
    func trigger(_ message: Message) {
      let handledMessage = self.onMessage(message)
      
      self.bindingsDel
        .filter( { return $0.event == message.event } )
        .forEach( { $0.callback.call(handledMessage) } )
    }
    
    
    func trigger(event: String,
                payload: Payload = [:],
                ref: String = "") {
      let message =  Message(ref: ref, event: event, payload: payload)
      self.trigger(message)
    }
    
}
