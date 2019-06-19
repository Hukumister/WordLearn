package ru.coderedwolf.wordlearn.ui.word.createword.list

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_add_example.*
import org.jetbrains.anko.onClick
import ru.coderedwolf.wordlearn.R

/**
 * @author CodeRedWolf. Date 07.06.2019.
 */
class AddExampleItem(val onAddClick: () -> Unit) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.addButton.onClick { onAddClick() }
    }

    override fun getLayout(): Int = R.layout.item_add_example

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        return other is AddExampleItem
    }

    override fun equals(other: Any?): Boolean {
        return other is AddExampleItem
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}