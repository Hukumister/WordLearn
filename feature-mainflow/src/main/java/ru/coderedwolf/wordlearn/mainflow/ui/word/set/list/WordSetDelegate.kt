package ru.coderedwolf.wordlearn.mainflow.ui.word.set.list

import androidx.annotation.ColorInt
import kotlinx.android.synthetic.main.item_word_set.view.*
import ru.coderedwolf.wordlearn.common.ui.adapter.BaseViewHolder
import ru.coderedwolf.wordlearn.common.ui.adapter.ClickableAdapter
import ru.coderedwolf.wordlearn.common.ui.adapter.ItemAdapterDelegate
import ru.coderedwolf.wordlearn.mainflow.R
import ru.coderedwolf.wordlearn.mainflow.ui.word.set.list.WordSetDelegate.RenderContact

/**
 * @author CodeRedWolf. Date 04.11.2019.
 */
class WordSetDelegate<ItemModel> : ItemAdapterDelegate<ItemModel, RenderContact>(), ClickableAdapter {

    interface RenderContact {
        val title: CharSequence
        @get:ColorInt val color: Int
    }

    override val layoutRes: Int
        get() = R.layout.item_word_set

    override fun isForViewType(item: ItemModel): Boolean = item is RenderContact

    override fun getItem(item: ItemModel): RenderContact = item as RenderContact

    override fun onBindView(item: RenderContact, holder: BaseViewHolder) = with(holder) {
        containerView.titleView.text = item.title
        containerView.imageView.setBackgroundColor(item.color)
    }

}