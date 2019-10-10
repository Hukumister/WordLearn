package ru.coderedwolf.wordlearn.common.ui.item

import android.view.View

/**
 * @author CodeRedWolf. Date 10.10.2019.
 */
class DefaultItemClicker<I : BaseItem>(
    private val clickConsumer: (I) -> Unit
) : BaseItemClicker<I>() {

    override fun onItemClick(item: I, view: View) = clickConsumer(item)
}