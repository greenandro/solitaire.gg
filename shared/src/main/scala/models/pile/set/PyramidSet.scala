package models.pile.set

import models.game.Rank
import models.pile.Pile
import models.pile.options.PyramidPileOptions
import models.rules.{ CardRemovalMethod, PyramidRules }

object PyramidSet {
  def apply(pyramidRules: PyramidRules, crm: CardRemovalMethod, lowRank: Rank): PyramidSet = {
    val prefix = if (pyramidRules.setNumber == 0) {
      "pyramid-"
    } else {
      s"pyramid${pyramidRules.setNumber + 1}-"
    }
    val pileOptions = PyramidPileOptions(pyramidRules, crm, lowRank)
    val piles = pileOptions.map {
      case (o, i, j) => Pile(s"$prefix$i-$j", o)
    }
    new PyramidSet(piles)
  }
}

class PyramidSet(piles: Seq[Pile]) extends PileSet("pyramid", piles)