package com.akkapersistenceexample

import akka.actor.ActorSystem

object App extends App {

  implicit val system = ActorSystem("example")

  val receiverActor   = system.actorOf(ReceiverActor.props)
  val persistentActor = system.actorOf(AtLeastOneActor.props(receiverActor))

}
