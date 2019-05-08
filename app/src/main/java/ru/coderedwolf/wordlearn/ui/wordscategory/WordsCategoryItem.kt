package ru.coderedwolf.wordlearn.ui.wordscategory

import com.bumptech.glide.Glide
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_category.view.*
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.model.Category

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
class WordsCategoryItem(val category: Category) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.categoryName.text = category.name
        Glide.with(viewHolder.itemView)
                .load(category.image)
                .into(viewHolder.itemView.categoryImage)
    }

    override fun getLayout() = R.layout.item_category
}