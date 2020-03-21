package ru.haroncode.wordlearn.common.ui.adapter

import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

abstract class BaseItemAdapterDelegate<ItemModel, ViewHolder : BaseViewHolder> :
    AbsListItemAdapterDelegate<ItemModel, ItemModel, ViewHolder>() {

    final override fun isForViewType(
        item: ItemModel,
        items: MutableList<ItemModel>,
        position: Int
    ) = throw UnsupportedOperationException("Method is unsupported use ItemAdapterDelegatesManager")
}
