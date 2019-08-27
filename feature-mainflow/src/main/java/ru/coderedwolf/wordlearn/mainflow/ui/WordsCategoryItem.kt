package ru.coderedwolf.wordlearn.mainflow.ui

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_category.*
import ru.coderedwolf.wordlearn.mainflow.R
import ru.coderedwolf.wordlearn.wordscategory.model.WordCategory

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */

class WordsCategoryItem(val wordCategory: WordCategory) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.categoryName.text = wordCategory.name
    }

    override fun getLayout() = R.layout.item_category

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        return if (other is WordsCategoryItem) {
            other.wordCategory.id == wordCategory.id
        } else {
            false
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is WordsCategoryItem) {
            other.wordCategory == wordCategory
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return wordCategory.hashCode()
    }
}