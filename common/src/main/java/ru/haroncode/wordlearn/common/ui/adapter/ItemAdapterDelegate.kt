package ru.haroncode.wordlearn.common.ui.adapter

import android.view.ViewGroup
import ru.haroncode.wordlearn.common.util.inflate

abstract class ItemAdapterDelegate<ItemModel, RenderContract> :
    BaseItemAdapterDelegate<ItemModel, BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        BaseViewHolder(parent.inflate(layoutRes))

    override fun onBindViewHolder(item: ItemModel, holder: BaseViewHolder, payloads: MutableList<Any>) {
        onBindView(getItem(item), holder, payloads)
    }

    open fun onBindView(item: RenderContract, holder: BaseViewHolder, payloads: MutableList<Any>) {
        onBindView(item, holder)
    }

    abstract val layoutRes: Int

    abstract fun onBindView(item: RenderContract, holder: BaseViewHolder)

    abstract fun getItem(item: ItemModel): RenderContract
}
