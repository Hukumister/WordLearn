package ru.coderedwolf.wordlearn.common.ui.adapter.delegate

import kotlinx.android.synthetic.main.list_item_button.view.*
import ru.coderedwolf.wordlearn.common.R
import ru.coderedwolf.wordlearn.common.ui.adapter.BaseViewHolder
import ru.coderedwolf.wordlearn.common.ui.adapter.ItemAdapterDelegate
import ru.coderedwolf.wordlearn.common.ui.adapter.delegate.ButtonAdapterDelegate.RenderContract

/**
 * @author CodeRedWolf. Date 29.10.2019.
 */
class ButtonAdapterDelegate<Item> : ItemAdapterDelegate<Item, RenderContract>() {

    interface RenderContract {
        val buttonText: CharSequence?
    }

    override val layoutRes: Int
        get() = R.layout.list_item_button

    override fun isForViewType(item: Item): Boolean = item is RenderContract

    override fun getItem(item: Item): RenderContract = item as RenderContract

    override fun onBindView(item: RenderContract, holder: BaseViewHolder) = with(holder) {
        itemView.button.text = item.buttonText
    }

}