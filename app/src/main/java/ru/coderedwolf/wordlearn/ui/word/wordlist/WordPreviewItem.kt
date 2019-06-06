package ru.coderedwolf.wordlearn.ui.word.wordlist

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_review_word.*
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.model.WordPreview

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
class WordPreviewItem(val wordPreview: WordPreview) : Item() {

    //todo заменить на нормальный текст
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val wordText = "${wordPreview.word} - ${wordPreview.translate}"
        viewHolder.wordText.text = wordText
        viewHolder.reviewCountText.text = wordPreview.reviewCount.toString()
    }

    override fun getLayout(): Int = R.layout.fragment_word_list
}