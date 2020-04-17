package ru.haroncode.wordlearn.mainflow.presentation.set

import ru.haroncode.wordlearn.common.domain.model.Product
import ru.haroncode.wordlearn.common.domain.model.Ticker
import ru.haroncode.wordlearn.common.domain.resource.FormattedText
import ru.haroncode.wordlearn.common.ui.adapter.SimpleComparableItem
import ru.haroncode.wordlearn.common.ui.adapter.delegate.TwoLineListItemAdapterDelegate

/**
 * @author HaronCode. Date 29.10.2019.
 */

data class WordSetViewState(
    val items: Product<List<Item>> = Product.Loading
) {

    sealed class Item : SimpleComparableItem() {

        data class WordSetItem(
            val id: Long,
            override val title: FormattedText,
            override val subtitle: FormattedText,
            override val ticker: Ticker
        ) : Item(), TwoLineListItemAdapterDelegate.RenderContract
    }
}
