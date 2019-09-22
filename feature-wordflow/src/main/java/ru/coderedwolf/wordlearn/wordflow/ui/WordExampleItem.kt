package ru.coderedwolf.wordlearn.wordflow.ui

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_word_example.*
import ru.coderedwolf.wordlearn.common.extension.onClick
import ru.coderedwolf.wordlearn.word.model.WordExample
import ru.coderedwolf.wordlearn.wordflow.R

/**
 * @author CodeRedWolf. Date 13.06.2019.
 */
class WordExampleItem(
    val wordExample: WordExample,
    val onRemoveClick: (WordExample) -> Unit
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.text.text = wordExample.example
        viewHolder.translation.text = wordExample.translation
        viewHolder.removeButton.onClick { onRemoveClick(wordExample) }
    }

    override fun getLayout(): Int = R.layout.item_word_example

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        return if (other is WordExampleItem) {
            other.wordExample == wordExample
        } else {
            false
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WordExampleItem

        if (wordExample != other.wordExample) return false

        return true
    }

    override fun hashCode(): Int {
        return wordExample.hashCode()
    }

}