package ru.coderedwolf.learnword.learnphrasesflow.ui

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import ru.coderedwolf.learnword.learnphrasesflow.R

/**
 * @author HaronCode. Date 14.06.2019.
 */
class LearnLoadingItem : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
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