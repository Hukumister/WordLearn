package ru.haroncode.wordlearn.common.ui.adapter

import android.view.View

typealias OnViewHolderClickListener = (BaseViewHolder, View) -> Unit

interface ClickableAdapter {

    fun bindClickListener(viewHolder: BaseViewHolder, listener: OnViewHolderClickListener) =
        viewHolder.containerView.setOnClickListener { view -> listener(viewHolder, view) }

    fun unbindClickListener(viewHolder: BaseViewHolder) = viewHolder.containerView.setOnClickListener(null)
}
