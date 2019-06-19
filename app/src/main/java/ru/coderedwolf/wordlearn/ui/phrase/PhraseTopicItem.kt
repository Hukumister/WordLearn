package ru.coderedwolf.wordlearn.ui.phrase

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_phrase_topic.view.*
import org.jetbrains.anko.onCheckedChange
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.model.phrase.PhraseTopic

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
class PhraseTopicItem(
        val phraseTopic: PhraseTopic,
        val onCheckedStudy: (PhraseTopic, Boolean) -> Unit
) : Item() {

    var isChecked: Boolean = phraseTopic.isStudy

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.checkbox.onCheckedChange { _, _ -> }

        viewHolder.itemView.checkbox.isChecked = isChecked
        viewHolder.itemView.topicTitle.text = phraseTopic.title

        viewHolder.itemView.checkbox.onCheckedChange { _, isChecked ->
            this.isChecked = isChecked
            onCheckedStudy.invoke(phraseTopic, isChecked)
        }
    }

    override fun getLayout(): Int = R.layout.item_phrase_topic

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