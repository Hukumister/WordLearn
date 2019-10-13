package ru.coderedwolf.wordlearn.common.ui.item

import android.view.View
import androidx.annotation.CallSuper
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.OnItemLongClickListener

/**
 * @author CodeRedWolf. Date 10.10.2019.
 */
abstract class BaseItem : Item<BaseViewHolder>() {

    interface ClickableItem {

        fun bindItemClickListener(itemClickListener: OnItemClickListener?)

        fun unBindClickListener()
    }

    interface LongClickableItem {

        fun bindItemLongClickListener(onItemLongClickListener: OnItemLongClickListener?)

        fun unBindLongClickListener()
    }

    @CallSuper
    override fun isSameAs(other: Item<*>?): Boolean = viewType != other?.viewType

    override fun createViewHolder(itemView: View): BaseViewHolder = BaseViewHolder(itemView)

    override fun bind(
        viewHolder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>,
        onItemClickListener: OnItemClickListener?,
        onItemLongClickListener: OnItemLongClickListener?
    ) {
        if (this is ClickableItem && this.isClickable) {
            bindItemClickListener(onItemClickListener)
        }

        if (this is LongClickableItem && this.isLongClickable) {
            bindItemLongClickListener(onItemLongClickListener)
        }
        super.bind(viewHolder, position, payloads, onItemClickListener, onItemLongClickListener)
    }

    override fun unbind(viewHolder: BaseViewHolder) {
        super.unbind(viewHolder)
        if (this is ClickableItem && this.isClickable) {
            unBindClickListener()
        }

        if (this is LongClickableItem && this.isLongClickable) {
            unBindLongClickListener()
        }
    }
}