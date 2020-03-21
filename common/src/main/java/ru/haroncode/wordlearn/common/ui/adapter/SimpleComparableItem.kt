package ru.haroncode.wordlearn.common.ui.adapter

abstract class SimpleComparableItem : ComparableItem {

    override fun areContentsTheSame(other: ComparableItem) = this == other

    override fun areItemsTheSame(other: ComparableItem) = this == other
}
