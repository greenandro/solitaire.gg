package services.history

import java.util.UUID

import com.github.mauricio.async.db.Connection
import models.history.GameHistory
import models.queries.BaseQueries
import models.queries.history.GameHistoryQueries
import models.queries.user.UserQueries
import org.joda.time.LocalDate
import utils.FutureUtils.defaultContext
import services.database.Database

import scala.concurrent.Future

object GameHistoryService {
  def getGameHistory(id: UUID) = Database.query(GameHistoryQueries.getById(id))

  def getById(id: UUID): Future[Option[GameHistory]] = Database.query(GameHistoryQueries.getById(id))

  def getAll(limit: Option[Int], offset: Option[Int]) = Database.query(GameHistoryQueries.getAll(limit, offset))

  def search(q: String, limit: Option[Int], offset: Option[Int]) = try {
    getById(UUID.fromString(q)).map(_.toSeq)
  } catch {
    case _: NumberFormatException => Database.query(GameHistoryQueries.search(q, "id desc", limit, offset))
  }

  def searchGames(q: String, orderBy: String, page: Int) = Database.query(GameHistoryQueries.searchCount(q)).flatMap { count =>
    Database.query(GameHistoryQueries.search(q, getOrderClause(orderBy), Some(BaseQueries.pageSize), Some(page * BaseQueries.pageSize))).map { list =>
      count -> list
    }
  }

  def getByUser(id: UUID, limit: Option[Int], offset: Option[Int]) = Database.query(GameHistoryQueries.GetByUser(id, limit, offset))

  def getCountByUser(id: UUID) = Database.query(GameHistoryQueries.getCountForUser(id))

  def getWins(d: LocalDate) = Database.query(GameHistoryQueries.GetGameHistoriesByDayAndStatus(d, "win")).flatMap { histories =>
    Future.sequence(histories.map { h =>
      Database.query(UserQueries.getById(h.player)).map(u => (h, u.getOrElse(throw new IllegalStateException())))
    })
  }

  def insert(gh: GameHistory) = Database.execute(GameHistoryQueries.insert(gh)).map(_ => true)

  def onComplete(gh: GameHistory) = Database.execute(GameHistoryQueries.OnComplete(gh)).flatMap {
    case 1 => Future.successful(true)
    case 0 => Database.execute(GameHistoryQueries.insert(gh)).flatMap { _ =>
      Database.execute(GameHistoryQueries.OnComplete(gh)).map(_ == 1)
    }
    case x => throw new IllegalStateException(s"Invalid return value [$x].")
  }

  def removeGameHistory(id: UUID, conn: Option[Connection]) = Database.execute(GameHistoryQueries.removeById(id), conn).map(_ == 1).map { success =>
    (id, success)
  }

  def removeGameHistoriesByUser(userId: UUID) = Database.query(GameHistoryQueries.GetGameHistoryIdsForUser(userId)).flatMap { gameIds =>
    Future.sequence(gameIds.map(id => removeGameHistory(id, None)))
  }

  private[this] def getOrderClause(orderBy: String) = orderBy match {
    case "game-id" => "id"
    case "created" => "created desc"
    case "completed" => "completed desc"
    case x => x
  }
}
