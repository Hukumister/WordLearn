package ru.coderedwolf.wordlearn.mainflow.presentation.set

import ru.coderedwolf.wordlearn.common.domain.result.Product
import ru.coderedwolf.wordlearn.common.ui.adapter.delegate.Button
import ru.coderedwolf.wordlearn.common.ui.adapter.delegate.ButtonAdapterDelegate
import ru.coderedwolf.wordlearn.mainflow.ui.word.set.list.WordSetDelegate

/**
 * @author HaronCode. Date 29.10.2019.
 */

data class WordSetUserViewState(
    val items: Product<List<Item>> = Product.Loading
) {

    sealed class Item {

        class AddWordSetItem(button: Button) : Item(), ButtonAdapterDelegate.RenderContract by button

        data class WordSetItem(
            override val title: CharSequence,
            override val color: Int
        ) : Item(), WordSetDelegate.RenderContact

    }
}
