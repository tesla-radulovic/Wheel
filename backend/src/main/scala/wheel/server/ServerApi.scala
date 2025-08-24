package wheel.server

import wvlet.airframe.http.{Endpoint, StaticContent}

class ServerApi:

  private val staticContent = StaticContent.fromDirectory("frontend")

  @Endpoint(path = "/*path")
  def pages(path: String) =
    staticContent(
      if path.isEmpty then "index.html" else path
    )
