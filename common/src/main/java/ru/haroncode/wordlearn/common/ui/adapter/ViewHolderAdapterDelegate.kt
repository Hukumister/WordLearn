package ru.haroncode.wordlearn.common.ui.adapter

import android.view.ViewGroup

abstract class ViewHolderAdapterDelegate<ItemModel, RenderContract, ViewHolder : BaseViewHolder> :
    BaseItemAdapterDelegate<ItemModel, ViewHolder>() {

    override fun onBindViewHolder(item: ItemModel, holder: ViewHolder, payloads: MutableList<Any>) =
        onBindView(getItem(item), holder, payloads)

    open fun onBindView(item: RenderContract, holder: ViewHolder, payloads: MutableList<Any>) =
        onBindView(item, holder)

    abstract fun onBindView(item: RenderContract, holder: ViewHolder)

    abstract fun getItem(item: ItemModel): RenderContract

    abstract override fun onCreateViewHolder(parent: ViewGroup): ViewHolder
}
