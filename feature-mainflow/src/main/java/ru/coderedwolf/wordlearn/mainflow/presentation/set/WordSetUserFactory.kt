package ru.coderedwolf.wordlearn.mainflow.presentation.set

import ru.coderedwolf.api.wordset.model.WordSet
import ru.coderedwolf.wordlearn.mainflow.presentation.set.WordSetUserViewState.Item.AddWordSetItem
import ru.coderedwolf.wordlearn.mainflow.presentation.set.WordSetUserViewState.Item.WordSetItem

/**
 * @author CodeRedWolf. Date 06.11.2019.
 */
object WordSetUserFactory {

    fun item(
        wordSetList: List<WordSet>
    ): List<WordSetUserViewState.Item> {
        val items = mutableListOf<WordSetUserViewState.Item>()

        items += AddWordSetItem
        items += wordSetList.map { wordSet -> WordSetItem(wordSet.title, wordSet.color) }

        return items
    }

}