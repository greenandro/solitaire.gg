// Generated rules for Scalataire.
package models.game.rules.generated

import models.game._
import models.game.rules._

/**
 * Original Settings:
 *   Deal order (RDd): 1|0|8
 *   Allowed pick ups/redeals (RDn): 2 (2)
 *   Shuffle before redealing (RDs): 1 (Yes)
 *   Enable stock (Sn): 0 (No stock)
 *   Tableau initial cards (T0d): -2 (custom)
 *   Custom initial cards (T0ds): UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU U
 *   Empty tableau is filled with (T0f): 5 (No card)
 *   Tableau piles (T0n): 18
 *   Tableau suit match rule for building (T0s): 4 (In alternating colors)
 *   Number of waste piles (W0n): 0
 *   Similar to (like): labellelucie
 *   Related games (related): threeshufflesandadraw, intelligence, linus, trefoil, superflowergarden
 */
object Linus extends GameRules(
  id = "linus",
  title = "Linus",
  like = Some("labellelucie"),
  related = Seq("threeshufflesandadraw", "intelligence", "linus", "trefoil", "superflowergarden"),
  description = "An easier variation of ^labellelucie^ where you build in alternate colors.",
  foundations = Seq(
    FoundationRules(
      numPiles = 4,
      wrapFromKingToAce = true,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      numPiles = 18,
      initialCards = InitialCards.Custom,
      customInitialCards = Seq(
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "UUU",
        "U"
      ),
      cardsFaceDown = TableauFaceDownCards.Count(0),
      suitMatchRuleForMovingStacks = SuitMatchRule.None
    )
  ),
  special = Some(
    SpecialRules(
      redealsAllowed = 2,
      dealOrder = DealOrder.ColumnsLeftToRightTopToBottom
    )
  ),
  complete = false
)

