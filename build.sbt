name := """akka-https"""

organization  := "com.github.pvoznenko"

version := "0.0.1"

scalaVersion := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val AkkaHttpVersion   = "2.4.8"
  val Json4sVersion     = "3.2.11"
  Seq(
    "com.typesafe.akka" %% "akka-http-testkit" % "2.4.8",
    "com.typesafe.akka" %% "akka-http-experimental" % "2.4.8",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  )
}

Revolver.settings
