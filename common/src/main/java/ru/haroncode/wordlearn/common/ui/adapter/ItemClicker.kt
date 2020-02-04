package ru.haroncode.wordlearn.common.ui.adapter

import android.view.View

/**
 * @author HaronCode.
 */
interface ItemClicker<ItemModel, VH : BaseViewHolder> {

    fun onBindClicker(holder: VH)

    fun invoke(holder: VH, view: View, item: ItemModel)

    fun onUnbindClicker(holder: VH)

    class NoneClicker<ItemModel, VH : BaseViewHolder> : ItemClicker<ItemModel, VH> {

        override fun onBindClicker(holder: VH) = Unit

        override fun invoke(holder: VH, view: View, item: ItemModel) = Unit

        override fun onUnbindClicker(holder: VH) = Unit

    }

}