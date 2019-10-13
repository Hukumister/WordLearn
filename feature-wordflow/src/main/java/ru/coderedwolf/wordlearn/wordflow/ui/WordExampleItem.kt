package ru.coderedwolf.wordlearn.wordflow.ui

import com.xwray.groupie.OnItemClickListener
import kotlinx.android.synthetic.main.item_word_example.*
import ru.coderedwolf.wordlearn.common.extension.onClick
import ru.coderedwolf.wordlearn.common.ui.item.BaseItem
import ru.coderedwolf.wordlearn.common.ui.item.BaseViewHolder
import ru.coderedwolf.wordlearn.word.model.WordExample
import ru.coderedwolf.wordlearn.wordflow.R

/**
 * @author CodeRedWolf. Date 13.06.2019.
 */
class WordExampleItem(val wordExample: WordExample) : BaseItem(), BaseItem.ClickableItem {

    private var clickListener: OnItemClickListener? = null

    override fun bind(viewHolder: BaseViewHolder, position: Int) = with(viewHolder) {
        text.text = wordExample.example
        translation.text = wordExample.translation
        removeButton.onClick { view -> clickListener?.onItemClick(this@WordExampleItem, view) }
    }

    override fun bindItemClickListener(itemClickListener: OnItemClickListener?) {
        clickListener = itemClickListener
    }

    override fun unBindClickListener() {
        clickListener = null
    }

    override fun getLayout(): Int = R.layout.item_word_example

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