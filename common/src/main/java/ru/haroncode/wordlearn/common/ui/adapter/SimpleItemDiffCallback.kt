package ru.haroncode.wordlearn.common.ui.adapter

import androidx.recyclerview.widget.DiffUtil

abstract class SimpleItemDiffCallback<T> : DiffUtil.ItemCallback<T>() {

    override fun getChangePayload(oldItem: T, newItem: T) = Unit
}
