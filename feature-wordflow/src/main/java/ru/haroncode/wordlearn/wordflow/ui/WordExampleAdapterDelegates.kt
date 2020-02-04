package ru.haroncode.wordlearn.wordflow.ui

import kotlinx.android.synthetic.main.item_add_example.view.*
import kotlinx.android.synthetic.main.item_word_example.view.*
import ru.haroncode.wordlearn.common.ui.adapter.BaseViewHolder
import ru.haroncode.wordlearn.common.ui.adapter.ClickableAdapter
import ru.haroncode.wordlearn.common.ui.adapter.ItemAdapterDelegate
import ru.haroncode.wordlearn.common.ui.adapter.OnViewHolderClickListener
import ru.haroncode.wordlearn.wordflow.R
import ru.haroncode.wordlearn.wordflow.presentation.CreateWordViewState.Item

/**
 * @author HaronCode.
 */
class WordExampleAdapterDelegates : ItemAdapterDelegate<Item, Item.WordExampleItem>(), ClickableAdapter {

    override val layoutRes: Int
        get() = R.layout.item_word_example

    override fun isForViewType(item: Item): Boolean = item is Item.WordExampleItem

    override fun getItem(item: Item): Item.WordExampleItem = item as Item.WordExampleItem

    override fun onBindView(item: Item.WordExampleItem, holder: BaseViewHolder) = with(holder) {
        itemView.text.text = item.wordExample.example
        itemView.translation.text = item.wordExample.translation
    }

    override fun bindClickListener(viewHolder: BaseViewHolder, listener: OnViewHolderClickListener) {
        viewHolder.itemView.removeButton.setOnClickListener { view -> listener(viewHolder, view) }
    }

    override fun unbindClickListener(viewHolder: BaseViewHolder) {
        viewHolder.itemView.removeButton.setOnClickListener(null)
    }

}

class CreateWordAdapterDelegate : ItemAdapterDelegate<Item, Item.AddButtonItem>(), ClickableAdapter {

    override val layoutRes: Int
        get() = R.layout.item_add_example

    override fun isForViewType(item: Item): Boolean = item is Item.AddButtonItem

    override fun onBindView(item: Item.AddButtonItem, holder: BaseViewHolder) = Unit

    override fun getItem(item: Item): Item.AddButtonItem = item as Item.AddButtonItem

    override fun bindClickListener(viewHolder: BaseViewHolder, listener: OnViewHolderClickListener) {
        viewHolder.itemView.addButton.setOnClickListener { view -> listener(viewHolder, view) }
    }

    override fun unbindClickListener(viewHolder: BaseViewHolder) {
        viewHolder.itemView.addButton.setOnClickListener(null)
    }

}