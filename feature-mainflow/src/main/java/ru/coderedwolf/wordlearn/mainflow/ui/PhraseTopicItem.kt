package ru.coderedwolf.wordlearn.mainflow.ui

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_category.view.*
import ru.coderedwolf.wordlearn.mainflow.R
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
class PhraseTopicItem(val phraseTopic: PhraseTopic) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.categoryName.text = phraseTopic.title
    }

    override fun getLayout(): Int = R.layout.item_category

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        return if (other is PhraseTopicItem) {
            other.phraseTopic.id == phraseTopic.id
        } else {
            false
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhraseTopicItem

        if (phraseTopic != other.phraseTopic) return false

        return true
    }

    override fun hashCode(): Int {
        return phraseTopic.hashCode()
    }
}