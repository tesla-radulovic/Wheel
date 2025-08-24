
package wheel.server

import wvlet.airframe.http.*
import wheel.api.Greeter

class GreeterImpl extends Greeter {
    override def greet (name: String) : String =
      println("hi")
      s"Zdravo!, ${name}"
  }
