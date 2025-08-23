val scala3Version = "3.3.6"
val airframeVersion = "2025.1.6"
val laminarVersion = "17.0.0"

ThisBuild / organization := "com.example"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := scala3Version

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-source:future",
    "-Yexplicit-nulls",
    "-Ysafe-init",
    "-rewrite",
    "-source:future-migration",
  )
)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "org.wvlet.airframe" %%% "airframe-http" % airframeVersion
    )
  )

lazy val backend = project
  .in(file("backend"))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "org.wvlet.airframe" %% "airframe-http-netty" % airframeVersion,
      "org.wvlet.airframe" %% "airframe-log" % airframeVersion
    )
  )
  .dependsOn(shared.jvm)

lazy val frontend = project
  .in(file("frontend"))
  .enablePlugins(ScalaJSPlugin, AirframeHttpPlugin)
  .settings(
    commonSettings,
    airframeHttpClients := Seq("wheel.api:rpc"),
    scalaJSUseMainModuleInitializer := true,  // Run main() on load
    libraryDependencies ++= Seq(
      "org.wvlet.airframe" %%% "airframe-http" % airframeVersion,
      "com.raquo" %%% "laminar" % laminarVersion,
      "org.scala-js" %%% "scalajs-dom" % "2.8.0"
    )
  )
  .dependsOn(shared.js)

lazy val root = project
  .in(file("."))
  .aggregate(shared.jvm, shared.js, backend, frontend)
