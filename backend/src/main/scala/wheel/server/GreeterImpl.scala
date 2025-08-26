
package wheel.server

import wvlet.airframe.http.*
import wheel.api.*

class GreeterImpl extends Greeter:
  override def greet (name: String) : String =
      println("hi")
      s"Zdravo!, ${name}"
  //override def parseNodeList (text: String) : Option[NodeList] = None //Helper.parse (text)
  override def transmorgify (member: Either[Student,Teacher]) : Either[Student,Teacher] = member match
    case Left( Student( name ) ) => Right( Teacher( name ) )
    case Right ( Teacher( name) ) => Left( Student ( name ) )
