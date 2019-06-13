package ru.coderedwolf.wordlearn.ui.word.createword.list

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_word_example.*
import org.jetbrains.anko.onClick
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.model.word.WordExample

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
}