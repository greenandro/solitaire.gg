// Generated rules for Scalataire.
package models.game.rules.generated

import models.game._
import models.game.rules._

/**
 * Original Settings:
 *   Foundation name (F0Nm): Aces Foundation
 *   Foundation initial cards (F0d): -1
 *   Number of foundation piles (F0n): 4 (4 stacks)
 *   TODO (F0u): 2
 *   Foundation name (F1Nm): Kings Foundation
 *   Foundation low rank (F1b): 22 (Deck's high card)
 *   Foundation initial cards (F1d): -1
 *   Number of foundation piles (F1n): 4 (4 stacks)
 *   Foundation rank match rule (F1r): 0x0020
 *   TODO (F1u): 2
 *   Foundation Sets (Fn): 2
 *   Auto-fill an empty tableau from (T0af): 4 (Stock)
 *   Tableau initial cards (T0d): 1 (1 card)
 *   Tableau piles (T0n): 20
 *   Tableau rank match rule for building (T0r): 0x0000
 *   Tableau suit match rule for building (T0s): 0 (May not build)
 *   Number of waste piles (W0n): 0
 *   Deal cards from stock (dealto): 2 (To all tableau piles)
 *   Number of decks (ndecks): 2 (2 decks)
 *   Related games (related): colorado
 */
object Twenty extends GameRules(
  id = "twenty",
  title = "Twenty",
  related = Seq("colorado"),
  description = "A somewhat mindless game with twenty reserve piles and no building.",
  deckOptions = DeckOptions(
    numDecks = 2
  ),
  stock = Some(
    StockRules(
      dealTo = StockDealTo.Tableau,
      maximumDeals = Some(1)
    )
  ),
  foundations = Seq(
    FoundationRules(
      name = "Aces Foundation",
      numPiles = 4,
      initialCards = 4,
      wrapFromKingToAce = true,
      autoMoveCards = true
    ),
    FoundationRules(
      name = "Kings Foundation",
      setNumber = 1,
      numPiles = 4,
      lowRank = FoundationLowRank.DeckHighRank,
      initialCards = 4,
      rankMatchRule = RankMatchRule.Down,
      wrapFromKingToAce = true,
      autoMoveCards = true
    )
  ),
  tableaus = Seq(
    TableauRules(
      numPiles = 20,
      initialCards = InitialCards.Count(1),
      cardsFaceDown = TableauFaceDownCards.Count(0),
      suitMatchRuleForBuilding = SuitMatchRule.None,
      rankMatchRuleForBuilding = RankMatchRule.None,
      suitMatchRuleForMovingStacks = SuitMatchRule.None,
      autoFillEmptyFrom = TableauAutoFillEmptyFrom.Stock,
      emptyFilledWith = TableauFillEmptyWith.Aces
    )
  ),
  complete = false
)

