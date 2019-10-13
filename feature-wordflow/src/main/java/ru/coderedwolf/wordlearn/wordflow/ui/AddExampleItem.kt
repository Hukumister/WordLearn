package ru.coderedwolf.wordlearn.wordflow.ui

import com.xwray.groupie.OnItemClickListener
import kotlinx.android.synthetic.main.item_add_example.*
import ru.coderedwolf.wordlearn.common.extension.onClick
import ru.coderedwolf.wordlearn.common.ui.item.BaseItem
import ru.coderedwolf.wordlearn.common.ui.item.BaseViewHolder
import ru.coderedwolf.wordlearn.wordflow.R

/**
 * @author CodeRedWolf. Date 07.06.2019.
 */
class AddExampleItem : BaseItem(), BaseItem.ClickableItem {

    private var listener: OnItemClickListener? = null

    override fun bindItemClickListener(itemClickListener: OnItemClickListener?) {
        listener = itemClickListener
    }

    override fun unBindClickListener() {
        listener = null
    }

    override fun bind(viewHolder: BaseViewHolder, position: Int) {
        viewHolder.addButton.onClick { button -> listener?.onItemClick(this, button) }
    }

    override fun getLayout(): Int = R.layout.item_add_example

    override fun equals(other: Any?): Boolean {
        return other is AddExampleItem
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}