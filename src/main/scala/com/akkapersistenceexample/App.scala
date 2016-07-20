package com.akkapersistenceexample

import akka.actor.{ Props, ActorSystem }

object App extends App {

  implicit val system = ActorSystem("example")
  val persistentActor = system.actorOf(Props(classOf[AtLeastOneActor]))
}
