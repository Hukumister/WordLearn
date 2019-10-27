package ru.coderedwolf.wordlearn.common.ui.adapter

import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.coderedwolf.wordlearn.common.util.inflate

/**
 * @author CodeRedWolf. Date 24.10.2019.
 */

abstract class ItemAdapterDelegate<BaseItemModel, RenderContract> :
    AbsListItemAdapterDelegate<BaseItemModel, BaseItemModel, BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        BaseViewHolder(parent.inflate(layoutRes))

    override fun onBindViewHolder(item: BaseItemModel, holder: BaseViewHolder, payloads: MutableList<Any>) {
        onBindView(getItem(item), holder, payloads)
    }

    override fun isForViewType(item: BaseItemModel, items: MutableList<BaseItemModel>, position: Int): Boolean =
        isForViewType(item)

    open fun onBindView(item: RenderContract, holder: BaseViewHolder, payloads: MutableList<Any>) {
        onBindView(item, holder)
    }

    abstract val layoutRes: Int

    abstract fun isForViewType(item: BaseItemModel): Boolean

    abstract fun onBindView(item: RenderContract, holder: BaseViewHolder)

    abstract fun getItem(item: BaseItemModel): RenderContract

}

