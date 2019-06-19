package ru.coderedwolf.wordlearn.ui.learn

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_learn_phrase.*
import org.jetbrains.anko.onClick
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.extension.visible
import ru.coderedwolf.wordlearn.model.learn.LearnPhrase

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
class LearnPhraseItem(val learnPhrase: LearnPhrase) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.visibleButton.onClick {
            it?.visible(false)
            viewHolder.phraseTranslation.visible(true)
        }

        viewHolder.visibleButton.visible(true)
        viewHolder.phraseTranslation.visible(false)

        viewHolder.phraseText.text = learnPhrase.phraseText
        viewHolder.phraseTranslation.text = learnPhrase.phraseTranslate
    }

    override fun getLayout(): Int = R.layout.item_learn_phrase

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        return if (other is LearnPhraseItem) {
            other.learnPhrase.phraseId == learnPhrase.phraseId
        } else {
            false
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LearnPhraseItem

        if (learnPhrase != other.learnPhrase) return false

        return true
    }

    override fun hashCode(): Int {
        return learnPhrase.hashCode()
    }


}