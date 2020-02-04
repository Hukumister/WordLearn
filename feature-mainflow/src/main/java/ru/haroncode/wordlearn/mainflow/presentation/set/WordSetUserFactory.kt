package ru.haroncode.wordlearn.mainflow.presentation.set

import ru.haroncode.api.wordset.model.WordSet
import ru.haroncode.wordlearn.common.domain.resource.toStringRes
import ru.haroncode.wordlearn.common.ui.adapter.delegate.Button
import ru.haroncode.wordlearn.mainflow.R
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserViewState.Item.AddWordSetItem
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserViewState.Item.WordSetItem

/**
 * @author HaronCode. Date 06.11.2019.
 */
object WordSetUserFactory {

    fun item(
        wordSetList: List<WordSet>
    ): List<WordSetUserViewState.Item> {
        val items = mutableListOf<WordSetUserViewState.Item>()

        items += AddWordSetItem(Button(R.string.action_add.toStringRes()))
        items += wordSetList.map { wordSet -> WordSetItem(wordSet.title, wordSet.color) }

        return items
    }

}