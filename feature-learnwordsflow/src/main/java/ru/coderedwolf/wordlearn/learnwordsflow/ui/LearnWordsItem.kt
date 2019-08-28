package ru.coderedwolf.wordlearn.learnwordsflow.ui

import androidx.core.view.isVisible
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_learn_word.*
import ru.coderedwolf.wordlearn.common.extension.onClick
import ru.coderedwolf.wordlearn.learnwordsflow.R
import ru.coderedwolf.wordlearn.learnwordsflow.model.LearnWord
import ru.coderedwolf.wordlearn.word.model.Word

/**
 * @author CodeRedWolf. Date 22.06.2019.
 */
class LearnWordsItem(
    val learnWord: LearnWord
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.wordTranslation.isVisible = false
        viewHolder.visibleButton.isVisible = true
        when {
            learnWord.isNewWord -> bindNewWord(viewHolder, learnWord.word)
            learnWord.isRotate -> bindRotateWord(viewHolder, learnWord.word)
            else -> bindSimpleWord(viewHolder, learnWord.word)
        }
    }

    private fun bindSimpleWord(viewHolder: ViewHolder, word: Word) {
        viewHolder.wordText.text = word.word
        viewHolder.wordTranslation.text = word.translation

        viewHolder.gotItTextView.text = viewHolder.itemView.context.getString(R.string.got_it)
        viewHolder.missItTextView.text = viewHolder.itemView.context.getString(R.string.missed_it)

        bindVisibleClick(viewHolder)
    }

    private fun bindRotateWord(viewHolder: ViewHolder, word: Word) {
        viewHolder.wordText.text = word.translation
        viewHolder.wordTranslation.text = word.word

        bindVisibleClick(viewHolder)
    }

    private fun bindNewWord(viewHolder: ViewHolder, word: Word) {
        bindSimpleWord(viewHolder, word)

        viewHolder.gotItTextView.text = viewHolder.itemView.context.getString(R.string.know)
        viewHolder.missItTextView.text = viewHolder.itemView.context.getString(R.string.not_know)
        bindVisibleClick(viewHolder)
    }

    private fun bindVisibleClick(viewHolder: ViewHolder) {
        viewHolder.visibleButton.onClick { button ->
            button.isVisible = false
            viewHolder.wordTranslation.isVisible = true
        }
    }

    override fun getLayout(): Int = R.layout.item_learn_word
}