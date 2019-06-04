package ru.coderedwolf.wordlearn.ui.wordscategory

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_category.*
import org.jetbrains.anko.onCheckedChange
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.model.Category

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */

class WordsCategoryItem(val category: Category) : Item() {

    var onChecked: (WordsCategoryItem, Boolean) -> Unit = { _, _ -> }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.checkbox.onCheckedChange { _, isChecked -> onChecked(this@WordsCategoryItem, isChecked) }
        viewHolder.checkbox.isChecked = category.isStudy
        viewHolder.categoryName.text = category.name
    }

    override fun getLayout() = R.layout.item_category

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        return if (other is WordsCategoryItem) {
            other.category.id == category.id
        } else {
            false
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is WordsCategoryItem) {
            other.category == category
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return category.hashCode()
    }
}