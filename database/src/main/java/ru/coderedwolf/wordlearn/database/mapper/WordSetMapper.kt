package ru.coderedwolf.wordlearn.database.mapper

import ru.coderedwolf.api.wordset.model.WordSet
import ru.coderedwolf.wordlearn.database.entity.WordSetEntity

/**
 * @author CodeRedWolf. Date 04.11.2019.
 */
class WordSetMapper {

    fun convertList(list: List<WordSetEntity>): List<WordSet> = list.map(::convertToModel)

    fun convertToModel(wordSetEntity: WordSetEntity) = WordSet(
        id = wordSetEntity.id ?: 0,
        title = wordSetEntity.title,
        color = wordSetEntity.color,
        isUserSet = wordSetEntity.isUserSet
    )

    fun convertToEntity(wordSet: WordSet) = WordSetEntity(
        id = if (wordSet.id != 0L) wordSet.id else null,
        isUserSet = wordSet.isUserSet,
        color = wordSet.color,
        title = wordSet.title
    )

}