package ru.coderedwolf.learnword.learnphrasesflow.ui

import androidx.core.view.isVisible
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_learn_phrase.*
import ru.coderedwolf.learnword.learnphrasesflow.R
import ru.coderedwolf.wordlearn.common.extension.onClick
import ru.coderedwolf.wordlearn.phrase.model.LearnPhrase

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
class LearnPhraseItem(val learnPhrase: LearnPhrase) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.visibleButton.onClick { button ->
            button.isVisible = false
            viewHolder.phraseTranslation.isVisible = true
        }

        viewHolder.visibleButton.isVisible = true
        viewHolder.phraseTranslation.isVisible = false

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