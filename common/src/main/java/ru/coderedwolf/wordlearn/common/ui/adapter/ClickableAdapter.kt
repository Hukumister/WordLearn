package ru.coderedwolf.wordlearn.common.ui.adapter

import android.view.View

/**
 * @author CodeRedWolf. Date 27.10.2019.
 */
typealias OnViewHolderClickListener = (BaseViewHolder, View) -> Unit

interface ClickableAdapter {

    fun bindClickListener(viewHolder: BaseViewHolder, listener: OnViewHolderClickListener)

    fun unbindClickListener(viewHolder: BaseViewHolder)

}