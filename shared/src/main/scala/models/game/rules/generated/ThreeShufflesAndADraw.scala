// Generated rules for Solitaire.gg.
package models.game.rules.generated

import models.game._
import models.game.rules._

/**
 * Original Settings:
 *   Deal order (RDd): 9 (Columns, left to right, top to bottom)
 *   Allowed pick ups/redeals (RDn): 2 (2)
 *   Shuffle before redealing (RDs): 1 (Yes)
 *   Enable stock (Sn): 0 (No stock)
 *   Tableau initial cards (T0d): -2 (custom)
 *   Custom initial cards (T0ds): UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU UUU U
 *   Empty tableau is filled with (T0f): 5 (No card)
 *   Tableau piles (T0n): 18
 *   Tableau suit match rule for building (T0s): 1 (In same suit)
 *   Number of waste piles (W0n): 0
 *   Draws must be after redeals (drawrule): 1
 *   Similar to (like): labellelucie
 *   Allowed draws (ndraw): 1 (1)
 */
object ThreeShufflesAndADraw extends GameRules(
  id = "threeshufflesandadraw",
  title = "Three Shuffles and a Draw",
  like = Some("labellelucie"),
  links = Seq(
    Link("Pretty Good Solitaire", "www.goodsol.com/pgshelp/three_shuffles_and_a_draw.htm"),
    Link("Rapture Technologies KingSol", "www.rapturetech.com/KingSol/Rules/ThreeShufflesandaDraw.htm"),
    Link("Wikipedia", "en.wikipedia.org/wiki/Three_Shuffles_and_a_Draw_(solitaire)")
  ),
  description = "A variation of ^labellelucie^ that adds a draw.",
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
      suitMatchRuleForBuilding = SuitMatchRule.SameSuit,
      suitMatchRuleForMovingStacks = SuitMatchRule.None
    )
  ),
  special = Some(
    SpecialRules(
      redealsAllowed = 2,
      dealOrder = DealOrder.ColumnsLeftToRightTopToBottom,
      drawsAllowed = 1,
      drawsAfterRedeals = true
    )
  )
)