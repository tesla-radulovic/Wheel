
package wheel.server

import wvlet.airframe.http.*
import wheel.api.*

class GreeterImpl extends Greeter:
  override def greet (name: String) : String =
      println("hi")
      s"Zdravo!, ${name}"
  override def parseNodeList (text: String) : Option[NodeList] = Helper.parse (text)
