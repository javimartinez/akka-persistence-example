package com.akkapersistenceexample

import akka.actor.Actor.Receive
import akka.actor.{ Actor, ActorLogging, Props }
import com.akkapersistenceexample.AtLeastOneActor.{ Confirm, Msg }

object ReceiverActor {

  def props(): Props = Props(classOf[ReceiverActor])

}

class ReceiverActor extends Actor with ActorLogging {

  override def receive: Receive = {

    case Msg(deliveryId, msg) =>
      log.info(s"ReceiverActor has received $msg ")
      sender() ! Confirm(deliveryId)
  }

}
