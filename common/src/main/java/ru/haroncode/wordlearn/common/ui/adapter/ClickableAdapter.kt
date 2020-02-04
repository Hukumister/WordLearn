package ru.haroncode.wordlearn.common.ui.adapter

import android.view.View

/**
 * @author HaronCode. Date 27.10.2019.
 */
typealias OnViewHolderClickListener = (BaseViewHolder, View) -> Unit

interface ClickableAdapter {

    fun bindClickListener(viewHolder: BaseViewHolder, listener: OnViewHolderClickListener) =
        viewHolder.containerView.setOnClickListener { view -> listener(viewHolder, view) }

    fun unbindClickListener(viewHolder: BaseViewHolder) = viewHolder.containerView.setOnClickListener(null)
}
