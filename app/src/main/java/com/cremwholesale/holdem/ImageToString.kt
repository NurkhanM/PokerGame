package com.cremwholesale.holdem

import android.widget.ImageView

class ImageToString(val cards: List<ImageView>) {

    override fun toString(): String {
        var str = ""
        val array = ArrayList<String>()
        cards.forEach { card ->
            if (card.toString() == "ac.png"){
                array.add(card.toString())
            }
        }

        str = array.joinToString()


        return str
        }
}
