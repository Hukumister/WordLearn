package ru.haroncode.wordlearn.common.ui.adapter.delegate

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.forEach
import kotlinx.android.extensions.LayoutContainer
import ru.haroncode.wordlearn.common.ui.adapter.BaseViewHolder
import ru.haroncode.wordlearn.common.ui.adapter.ClickableAdapter
import ru.haroncode.wordlearn.common.ui.adapter.ItemAdapterDelegate
import ru.haroncode.wordlearn.common.ui.adapter.OnViewHolderClickListener
import ru.haroncode.wordlearn.common.util.inflate

class StaticAdapterDelegate<Item>(
    @LayoutRes override val layoutRes: Int,
    private val clickableViewIds: IntArray = IntArray(0),
    private val binder: (BaseViewHolder) -> Unit = {}
) : ItemAdapterDelegate<Item, Item>(), ClickableAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = StaticViewHolder(parent.inflate(layoutRes), clickableViewIds)

    override fun getItem(item: Item) = item

    override fun onBindView(item: Item, holder: BaseViewHolder) = binder(holder)

    override fun bindClickListener(
        viewHolder: BaseViewHolder,
        listener: OnViewHolderClickListener
    ) {
        val staticViewHolder = viewHolder as StaticViewHolder
        staticViewHolder.setOnClickListener(listener)
    }

    override fun unbindClickListener(viewHolder: BaseViewHolder) {
        val staticViewHolder = viewHolder as StaticViewHolder
        staticViewHolder.setOnClickListener(null)
    }

    class StaticViewHolder(
        override val containerView: View,
        clickableViewIds: IntArray
    ) : BaseViewHolder(containerView), LayoutContainer {

        private var clickListener: OnViewHolderClickListener? = null

        private val clickableViews: List<View> = if (clickableViewIds.isEmpty()) {
            findClickableViews(containerView)
        } else {
            clickableViewIds.map { id -> itemView.findViewById<View>(id) }
        }

        init {
            clickableViews.forEach { view -> view.setOnClickListener(::onClick) }
        }

        fun setOnClickListener(listener: OnViewHolderClickListener?) {
            clickListener = listener
            clickableViews.forEach { view -> view.isClickable = listener != null }
        }

        private fun onClick(view: View) {
            clickListener?.invoke(this, view)
        }

        private fun findClickableViews(view: View): List<View> {
            val clickableItems = mutableListOf<View>()
            if (view.isClickable) {
                clickableItems += view
            }
            if (view is ViewGroup) {
                view.forEach { child ->
                    clickableItems += findClickableViews(child)
                }
            }
            return clickableItems
        }
    }
}
