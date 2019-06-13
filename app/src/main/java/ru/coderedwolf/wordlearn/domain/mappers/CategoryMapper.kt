package ru.coderedwolf.wordlearn.domain.mappers

import ru.coderedwolf.wordlearn.model.entity.WordCategoryEntity
import ru.coderedwolf.wordlearn.model.word.WordCategory
import java.util.*
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
class CategoryMapper @Inject constructor() {

    fun convert(wordCategoryEntity: WordCategoryEntity): WordCategory {
        return WordCategory(
            id = wordCategoryEntity.id ?: -1,
            name = wordCategoryEntity.name,
            isStudy = wordCategoryEntity.isStudy,
            progress = 0
        )
    }

    fun convertToEntity(wordCategory: WordCategory): WordCategoryEntity {
        return WordCategoryEntity(
                id = wordCategory.id,
                name = wordCategory.name,
                isStudy = wordCategory.isStudy,
                createDate = Date()
        )
    }
}