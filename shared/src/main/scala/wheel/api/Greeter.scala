package wheel.api

import wvlet.airframe.http.*

@RPC
trait Greeter {
  def greet(name: String): String = s"Hello, $name!"
  def getTime: String = java.time.Instant.now.toString
}

object Greeter extends RxRouterProvider {
  override def router: RxRouter = RxRouter.of[Greeter]
}
