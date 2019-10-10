package ru.coderedwolf.wordlearn.common.ui.item

import android.view.View
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener

/**
 * @author CodeRedWolf. Date 10.10.2019.
 */
abstract class BaseItemClicker<I : BaseItem> : OnItemClickListener {

    @Suppress("UNCHECKED_CAST")
    override fun onItemClick(item: Item<*>, view: View) {
        (item as? I)?.let { typedItem -> onItemClick(typedItem, view) }
    }

    abstract fun onItemClick(item: I, view: View)
}