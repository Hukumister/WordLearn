package ru.haroncode.wordlearn.common.ui.adapter.delegate

import kotlinx.android.synthetic.main.list_item_two_line.view.*
import ru.haroncode.wordlearn.common.R
import ru.haroncode.wordlearn.common.domain.model.Ticker
import ru.haroncode.wordlearn.common.domain.resource.FormattedText
import ru.haroncode.wordlearn.common.domain.system.ImageLoader
import ru.haroncode.wordlearn.common.ui.adapter.BaseViewHolder
import ru.haroncode.wordlearn.common.ui.adapter.ClickableAdapter
import ru.haroncode.wordlearn.common.ui.adapter.ItemAdapterDelegate
import ru.haroncode.wordlearn.common.ui.adapter.delegate.TwoLineListItemAdapterDelegate.RenderContract

class TwoLineListItemAdapterDelegate<Item>(
    private val imageLoader: ImageLoader
) : ItemAdapterDelegate<Item, RenderContract>(), ClickableAdapter {

    interface RenderContract {
        val title: FormattedText
        val subtitle: FormattedText
        val ticker: Ticker
        val maxSubtitleLines: Int
            get() = 2
        val titleCaption: FormattedText?
            get() = null
    }

    override val layoutRes = R.layout.list_item_two_line

    override fun onBindView(item: RenderContract, holder: BaseViewHolder) = with(holder) {
        itemView.titleView.text = item.title.format()
        itemView.subtitleView.text = item.subtitle.format()
        itemView.subtitleView.maxLines = item.maxSubtitleLines
        itemView.captionTextView.text = item.titleCaption?.format()

        imageLoader.loadTicker(item.ticker, itemView.imageView)
    }

    override fun getItem(item: Item) = item as RenderContract
}
