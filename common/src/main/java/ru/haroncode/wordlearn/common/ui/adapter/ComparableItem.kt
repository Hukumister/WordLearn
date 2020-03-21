package ru.haroncode.wordlearn.common.ui.adapter

interface ComparableItem {

    fun areContentsTheSame(other: ComparableItem): Boolean

    fun areItemsTheSame(other: ComparableItem): Boolean
}
