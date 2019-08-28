package ru.coderedwolf.wordlearn.common.ui

import android.view.View
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
interface CardStackListenerSimple : CardStackListener {

    override fun onCardDisappeared(view: View?, position: Int) {
        //ignore
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        //ignore
    }

    override fun onCardSwiped(direction: Direction) {
        //ignore
    }

    override fun onCardCanceled() {
        //ignore
    }

    override fun onCardAppeared(view: View?, position: Int) {
        //ignore
    }

    override fun onCardRewound() {
        //ignore
    }
}