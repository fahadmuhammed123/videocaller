
import Foundation

public class User {
  
  public let name: String
  
  public let id: String

  public let avatar: String
  
  public let email: String

    init(name: String = "", id: String = "", avatar: String = "", email: String = "") {
    self.name = name
    self.id = id
    self.avatar = avatar
    self.email = email
  }
}
