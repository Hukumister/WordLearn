package ru.coderedwolf.wordlearn.ui.learn

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import ru.coderedwolf.wordlearn.R

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
class LearnLoadingItem : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        //ignore
    }

    override fun getLayout(): Int = R.layout.item_learn_loading

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}