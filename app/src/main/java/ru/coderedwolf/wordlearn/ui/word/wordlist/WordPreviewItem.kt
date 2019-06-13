package ru.coderedwolf.wordlearn.ui.word.wordlist

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_preview_word.*
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.model.word.WordPreview

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
class WordPreviewItem(val wordPreview: WordPreview) : Item() {

    //todo заменить на нормальный текст
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val wordText = "${wordPreview.word} - ${wordPreview.translation}"
        viewHolder.wordText.text = wordText
        viewHolder.reviewCountText.text = wordPreview.reviewCount.toString()
    }

    override fun getLayout(): Int = R.layout.item_preview_word

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        return if (other is WordPreviewItem) {
            other.wordPreview.wordId == wordPreview.wordId
        } else {
            false
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is WordPreviewItem) {
            other.wordPreview == wordPreview
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return wordPreview.hashCode()
    }
}