package models.game

import java.util.UUID

import models._

class RequestMessageHandler(userId: UUID, gs: GameState, undo: UndoHelper, send: (ResponseMessage, Boolean) => Unit, registerMove: RequestMessage => Unit) {
  private[this] def sendSeq(rms: Seq[ResponseMessage], registerUndoResponse: Boolean): Unit = if (rms.size == 1) {
    val msg = rms.headOption.getOrElse(throw new IllegalStateException())
    send(msg, registerUndoResponse)
  } else {
    send(MessageSet(rms), registerUndoResponse)
  }

  def handle(msg: RequestMessage) = msg match {
    case sc: SC => handleSelectCard(userId, sc)
    case sp: SP => handleSelectPile(userId, sp)
    case mc: MC => handleMoveCards(userId, mc)
    case UN => send(undo.undo(gs), false)
    case RE => send(undo.redo(gs), false)
    case _ => throw new IllegalStateException(s"Unhandled request message [$msg].")
  }

  private[this] def handleSelectCard(userId: UUID, sc: SC) = {
    val card = gs.cardsById(sc.card)
    val pile = gs.pilesById(sc.pile)
    if (!pile.cards.contains(card)) {
      throw new IllegalStateException(s"SelectCard for game [${gs.gameId}]: Card [${card.toString}] is not part of the [${sc.pile}] pile.")
    }
    if (pile.canSelectCard(card, gs)) {
      val messages = pile.onSelectCard(card, gs)
      sendSeq(messages, registerUndoResponse = true)
      if (messages.size != 1 || messages.headOption.forall {
        case _: CardRevealed => false
        case _ => true
      }) {
        registerMove(sc)
      }
    }
  }

  private[this] def handleSelectPile(userId: UUID, sp: SP) = {
    val pile = gs.pilesById(sp.pile)
    if (pile.cards.nonEmpty) {
      throw new IllegalStateException(s"SelectPile [${sp.pile}] called on a non-empty deck.")
    }
    if (pile.canSelectPile(gs)) {
      val messages = pile.onSelectPile(gs)
      sendSeq(messages, registerUndoResponse = true)
      registerMove(sp)
    }
  }

  private[this] def handleMoveCards(userId: UUID, mc: MC) = {
    val cards = mc.cards.map(gs.cardsById)
    val sourcePile = gs.pilesById(mc.src)
    val targetPile = gs.pilesById(mc.tgt)
    for (c <- cards) {
      if (!sourcePile.cards.contains(c)) {
        throw new IllegalArgumentException(s"Card [$c] is not a part of source pile [${sourcePile.id}].")
      }
    }
    if (sourcePile.canDragFrom(cards, gs)) {
      if (targetPile.canDragTo(sourcePile, cards, gs)) {
        val messages = targetPile.onDragTo(sourcePile, cards, gs)
        sendSeq(messages, registerUndoResponse = true)
        registerMove(mc)
      } else {
        send(CardMoveCancelled(mc.cards, mc.src), false)
      }
    } else {
      send(CardMoveCancelled(mc.cards, mc.src), false)
    }
  }
}
