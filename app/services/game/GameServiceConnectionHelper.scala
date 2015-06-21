package services.game

import java.util.UUID

import akka.actor.ActorRef
import models._
import models.user.PlayerRecord
import play.api.libs.concurrent.Akka

trait GameServiceConnectionHelper { this: GameService =>
  protected[this] def handleAddPlayer(userId: UUID, name: String, connectionId: UUID, connectionActor: ActorRef) {
    if (player.userId == userId) {
      player.connectionActor.foreach(_ ! Disconnected("Joined from another connection."))
      player.connectionId = Some(connectionId)
      player.connectionActor = Some(connectionActor)
    } else {
      // playerConnections += PlayerRecord(userId, name, Some(connectionId), Some(connectionActor))
      gameState.addPlayer(userId, name)
      throw new NotImplementedError()
    }

    connectionActor ! GameStarted(id, self, started)
    connectionActor ! GameJoined(id, gameState.view(userId), possibleMoves(Some(userId)))
  }

  protected[this] def handleAddObserver(userId: UUID, name: String, connectionId: UUID, connectionActor: ActorRef, as: Option[UUID]) {
    observerConnections += (PlayerRecord(userId, name, Some(connectionId), Some(connectionActor)) -> as)
    val gs = as match {
      case Some(p) => gameState.view(p)
      case None => gameState
    }
    connectionActor ! GameJoined(id, gs, possibleMoves(None))
  }

  protected[this] def handleConnectionStopped(connectionId: UUID) {
    import play.api.Play.current
    import play.api.libs.concurrent.Execution.Implicits.defaultContext

    import scala.concurrent.duration._

    if (player.connectionId.contains(connectionId)) {
      log.info(s"Player connection [$connectionId] stopped.")
      player.connectionId = None
      player.connectionActor = None
    }
    observerConnections.find(_._1.connectionId.contains(connectionId)) match {
      case Some(observerConnection) =>
        log.info(s"Observer connection [$connectionId] stopped.")
        observerConnection._1.connectionId = None
        observerConnection._1.connectionActor = None
      case None => // noop
    }

    val hasPlayer = player.connectionId.isDefined || observerConnections.exists(_._1.connectionId.isDefined)
    if (!hasPlayer) {
      Akka.system.scheduler.scheduleOnce(30.seconds, self, StopGameIfEmpty)
    }
  }
}
