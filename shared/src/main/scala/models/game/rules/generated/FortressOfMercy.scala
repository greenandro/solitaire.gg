// Generated rules for Scalataire.
package models.game.rules.generated

import models.game._
import models.game.rules._

/**
 * Original Settings:
 *   Foundation initial cards (F0d): 2 (2 cards)
 *   Enable stock (Sn): 0 (No stock)
 *   Tableau initial cards (T0d): 5 (5 cards)
 *   Tableau piles (T0n): 10
 *   Tableau rank match rule for building (T0r): 0x0080|0x0020
 *   Tableau suit match rule for building (T0s): 1 (In same suit)
 *   Number of waste piles (W0n): 0
 *   Similar to (like): fortress
 *   Allowed draws (ndraw): 1 (1)
 *   Related games (related): bastion, chessboard, fortressofmercy, beleagueredfortress
 *   Enable super moves, whatever those are (supermoves): 1
 */
object FortressOfMercy extends GameRules(
  id = "fortressofmercy",
  title = "Fortress of Mercy",
  like = Some("fortress"),
  related = Seq("bastion", "chessboard", "fortressofmercy", "beleagueredfortress"),
  description = "A variation of ^fortress^ that allows you one \"merci\" move, in which any one card can be moved to the top of its stack.",
  foundations = Seq(
    FoundationRules(
      numPiles = 4,
      initialCards = 2,
      wrapFromKingToAce = true,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      numPiles = 10,
      initialCards = InitialCards.Count(5),
      cardsFaceDown = TableauFaceDownCards.Count(0),
      suitMatchRuleForBuilding = SuitMatchRule.SameSuit,
      rankMatchRuleForBuilding = RankMatchRule.UpOrDown,
      suitMatchRuleForMovingStacks = SuitMatchRule.None,
      emptyFilledWith = TableauFillEmptyWith.Aces
    )
  ),
  special = Some(
    SpecialRules(
      shuffleBeforeRedeal = false,
      drawsAllowed = 1
    )
  ),
  complete = false
)

