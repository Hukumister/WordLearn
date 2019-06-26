package ru.coderedwolf.wordlearn.ui.learn.word

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_learn_word.*
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.model.learn.LearnWord
import ru.coderedwolf.wordlearn.model.word.Word

/**
 * @author CodeRedWolf. Date 22.06.2019.
 */
class LearnWordsItem(val learnWord: LearnWord) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) = when {
        learnWord.isNewWord -> bindNewWord(viewHolder, learnWord.word)
        learnWord.isRotate -> bindRotateWord(viewHolder, learnWord.word)
        else -> bindSimpleWord(viewHolder, learnWord.word)
    }

    private fun bindSimpleWord(viewHolder: ViewHolder, word: Word) {
        viewHolder.wordText.text = word.word
        viewHolder.wordTranslation.text = word.translation
    }

    private fun bindRotateWord(viewHolder: ViewHolder, word: Word) {
        viewHolder.wordText.text = word.translation
        viewHolder.wordTranslation.text = word.word
    }

    private fun bindNewWord(viewHolder: ViewHolder, word: Word) {
        bindSimpleWord(viewHolder, word)
    }

    override fun getLayout(): Int = R.layout.item_learn_word
}