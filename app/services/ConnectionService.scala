package services

import akka.actor.{Props, ActorRef}
import models._
import models.game.{Pile, GameState, Deck}
import utils.Config
import utils.metrics.InstrumentedActor

object ConnectionService {
  def props(out: ActorRef) = Props(new ConnectionService(out))
}

class ConnectionService(out: ActorRef) extends InstrumentedActor {
  override def receiveRequest = {
    case mr: MalformedRequest => out ! ServerError("MalformedRequest", mr.content)
    case p: Ping => out ! Pong(p.timestamp)
    case GetVersion => out ! VersionResponse(Config.version)
    case jg: JoinGame => out ! GameJoined(Nil, GameState.default(Deck.fresh, Pile.default))
    case x => throw new IllegalArgumentException("Unhandled message [" + x.getClass.getSimpleName + "].")
  }
}
