package wheel.server

import wheel.api.Greeter
import wvlet.airframe.http.netty.*
import wvlet.airframe.http.RxRouter
import wvlet.log.LogSupport

object Server extends LogSupport {

  
  def main(args: Array[String]): Unit = {
    val router = RxRouter.of[GreeterImpl]  // Auto-generates routes from RPC trait

    Netty.server
      .withRouter(router)
      .withPort(8080)
      .start { server =>
        info(s"Server started at http://localhost")
        server.awaitTermination()
      }
  }
}

