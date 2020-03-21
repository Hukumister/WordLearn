package ru.haroncode.wordlearn.common.ui.adapter

class NoneDiffUtilItemCallback<Item : Any> : SimpleItemDiffCallback<Item>() {

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = false

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = false
}
