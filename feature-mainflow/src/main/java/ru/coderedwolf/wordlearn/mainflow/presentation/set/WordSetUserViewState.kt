package ru.coderedwolf.wordlearn.mainflow.presentation.set

import ru.coderedwolf.wordlearn.common.domain.result.Product
import ru.coderedwolf.wordlearn.mainflow.ui.word.set.list.WordSetDelegate

/**
 * @author CodeRedWolf. Date 29.10.2019.
 */

data class WordSetUserViewState(
    val items: Product<List<Item>> = Product.Loading
) {

    sealed class Item {

        object AddWordSetItem : Item()

        data class WordSetItem(
            override val title: CharSequence,
            override val color: Int
        ) : Item(), WordSetDelegate.RenderContact

    }
}
