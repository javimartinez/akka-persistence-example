package com.akkapersistenceexample

import akka.actor.{ ActorRef, Props }
import akka.persistence.{ AtLeastOnceDelivery, PersistentActor }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object AtLeastOneActor {

  def props(receiverActorRef: ActorRef): Props =
    Props(classOf[AtLeastOneActor], receiverActorRef)

  sealed trait Evt
  case class MsgSent(s: String)             extends Evt
  case class MsgConfirmed(deliveryId: Long) extends Evt

  sealed trait Command
  case class SendMsgToReceiveActor(msg: String) extends Command
  case class Msg(deliveryId: Long, s: String)   extends Command
  case class Confirm(deliveryId: Long)          extends Command

}

class AtLeastOneActor(receiverActorRef: ActorRef)
    extends PersistentActor
    with AtLeastOnceDelivery {
  import AtLeastOneActor._

  override def persistenceId: String = "at-least-one-actor"

  val scheduler = context.system.scheduler
    .schedule(1.seconds, 1.seconds, self, SendMsgToReceiveActor("Hello world"))

  override def receiveCommand: Receive = {

    case SendMsgToReceiveActor(msg) => persist(MsgSent(msg))(updateState)

    case Confirm(deliveryId) => persist(MsgConfirmed(deliveryId))(updateState)

  }

  override def receiveRecover: Receive = {
    case evt: Evt => updateState(evt)
  }

  def updateState(evt: Evt): Unit = evt match {

    case MsgSent(s) =>
      deliver(receiverActorRef.path)(deliveryId => Msg(deliveryId, s))

    case MsgConfirmed(deliveryId) => confirmDelivery(deliveryId)
  }
}
