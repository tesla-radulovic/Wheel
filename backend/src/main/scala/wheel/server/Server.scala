package wheel.server

import wheel.api.Greeter
import wvlet.airframe.http.netty.Netty
import wvlet.airframe.http.RxRouter
import wvlet.log.LogSupport

object Server extends LogSupport {
  def main(args: Array[String]): Unit = {
    val router = RxRouter.of[Greeter]  // Auto-generates routes from RPC trait

    Netty.server
      .withRouter(router)
      .design
      .run { server =>
        info(s"Server started at http://localhost")
        // server.awaitTermination()
      }
  }
}
