// Generated rules for Scalataire.
package models.game.rules.generated

import models.game._
import models.game.rules._

/**
 * Original Settings:
 *   Foundation initial cards (F0d): -1
 *   Enable stock (Sn): 0 (No stock)
 *   Tableau initial cards (T0d): 6 (6 cards)
 *   Tableau action during deal (T0dd): 3 (Move cards to foundations)
 *   Tableau piles (T0n): 8
 *   Tableau suit match rule for building (T0s): 5 (Regardless of suit)
 *   Number of waste piles (W0n): 0
 *   Similar to (like): beleagueredcastle
 *   Related games (related): exiledkings
 *   Enable super moves, whatever those are (supermoves): 1
 */
object Citadel extends GameRules(
  id = "citadel",
  title = "Citadel",
  like = Some("beleagueredcastle"),
  related = Seq("exiledkings"),
  description = "An easier variation of ^beleagueredcastle^ where cards are moved to the foundation during the deal.",
  foundations = Seq(
    FoundationRules(
      numPiles = 4,
      initialCards = 4,
      wrapFromKingToAce = true,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      numPiles = 8,
      initialCards = InitialCards.Count(6),
      cardsFaceDown = TableauFaceDownCards.Count(0),
      suitMatchRuleForBuilding = SuitMatchRule.Any,
      suitMatchRuleForMovingStacks = SuitMatchRule.None,
      emptyFilledWith = TableauFillEmptyWith.Aces,
      actionDuringDeal = PileAction.MoveToFoundation
    )
  ),
  complete = false
)

