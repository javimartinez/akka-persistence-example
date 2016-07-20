package com.akkapersistenceexample

import akka.actor.Props
import akka.persistence.{ AtLeastOnceDelivery, PersistentActor }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object AtLeastOneActor {

  def props: Props = Props(classOf[AtLeastOneActor])

  sealed trait Evt
  case class MsgSent(s: String)             extends Evt
  case class MsgConfirmed(deliveryId: Long) extends Evt

  case class Msg(deliveryId: Long, s: String)
  case class Confirm(deliveryId: Long)

}

class AtLeastOneActor extends PersistentActor with AtLeastOnceDelivery {
  import AtLeastOneActor._

  override def persistenceId: String = "persistence-id"

  val scheduler =
    context.system.scheduler.schedule(1.seconds, 1.seconds, self, "Hello")

  override def receiveCommand: Receive = {

    case s: String => persist(MsgSent(s))(updateState)

    case Confirm(deliveryId) => persist(MsgConfirmed(deliveryId))(updateState)

    case Msg(deliveryId, s) =>
      println(deliveryId)
  }

  override def receiveRecover: Receive = {
    case evt: Evt => updateState(evt)
  }

  def updateState(evt: Evt): Unit = evt match {

    case MsgSent(s) =>
      deliver(self.path)(deliveryId => Msg(deliveryId, s))

    case MsgConfirmed(deliveryId) => confirmDelivery(deliveryId)
  }
}
