import sbt._

object Version {
  final val Scala     = "2.11.8"
  final val ScalaTest = "3.0.0-RC4"
  final val actorVersion = "2.4.8"
  final val persistenceVersion = "2.4.8"
}


object Library {
  val scalaTest = "org.scalatest" %% "scalatest" % Version.ScalaTest
  val actor = "com.typesafe.akka" %% "akka-actor" % Version.actorVersion
  val persistence = "com.typesafe.akka" %% "akka-persistence" % Version.persistenceVersion

}

