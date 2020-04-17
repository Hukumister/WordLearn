package ru.haroncode.wordlearn.mainflow.presentation.set

import ru.haroncode.api.wordset.model.WordSet
import ru.haroncode.wordlearn.common.domain.model.Ticker
import ru.haroncode.wordlearn.common.domain.resource.EmptyFormatted
import ru.haroncode.wordlearn.common.domain.resource.toFormatted
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetViewState.Item

/**
 * @author HaronCode.
 */
object WordSetFactory {

    fun item(
        wordSetList: List<WordSet>
    ) = wordSetList.map { set ->
        Item.WordSetItem(
            id = set.id,
            title = set.title.toFormatted(),
            subtitle = EmptyFormatted,
            ticker = Ticker(set.title, set.color)
        )
    }
}
