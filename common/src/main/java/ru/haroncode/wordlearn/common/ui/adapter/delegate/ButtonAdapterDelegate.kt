package ru.haroncode.wordlearn.common.ui.adapter.delegate

import kotlinx.android.synthetic.main.list_item_button.view.*
import ru.haroncode.wordlearn.common.domain.resource.FormattedText
import ru.haroncode.wordlearn.common.ui.adapter.BaseViewHolder
import ru.haroncode.wordlearn.common.ui.adapter.ClickableAdapter
import ru.haroncode.wordlearn.common.ui.adapter.ItemAdapterDelegate
import ru.haroncode.wordlearn.common.ui.adapter.OnViewHolderClickListener
import ru.haroncode.wordlearn.common.ui.adapter.delegate.ButtonAdapterDelegate.RenderContract

data class Button(override val buttonText: FormattedText?) : RenderContract

class ButtonAdapterDelegate<Item> : ItemAdapterDelegate<Item, RenderContract>(), ClickableAdapter {

    interface RenderContract {
        val buttonText: FormattedText?
    }

    override val layoutRes: Int = ru.haroncode.wordlearn.common.R.layout.list_item_button

    override fun getItem(item: Item): RenderContract = item as RenderContract

    override fun onBindView(item: RenderContract, holder: BaseViewHolder) = with(holder) {
        itemView.button.text = item.buttonText?.format()
    }

    override fun bindClickListener(
        viewHolder: BaseViewHolder,
        listener: OnViewHolderClickListener
    ) = with(viewHolder) {
        itemView.button.setOnClickListener { view -> listener.invoke(this, view) }
    }

    override fun unbindClickListener(viewHolder: BaseViewHolder) = with(viewHolder) {
        itemView.button.setOnClickListener(null)
    }
}
