package ru.coderedwolf.wordlearn.common.ui.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * @author CodeRedWolf. Date 26.10.2019.
 */

class NoneDiffUtilItemCallback<Item : Any> : DiffUtil.ItemCallback<Item>() {

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = false

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = false

}