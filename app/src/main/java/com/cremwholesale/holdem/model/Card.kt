package com.cremwholesale.holdem.model

import com.cremwholesale.holdem.Suit
import com.cremwholesale.holdem.Weight

data class Card(val weight: Weight, val suit: Suit) {

    override fun toString() = "$weight:$suit"

}