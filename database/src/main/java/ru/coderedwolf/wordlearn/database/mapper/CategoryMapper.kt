package ru.coderedwolf.wordlearn.database.mapper

import org.threeten.bp.Instant
import ru.coderedwolf.wordlearn.database.entity.WordCategoryEntity
import ru.coderedwolf.wordlearn.wordscategory.model.WordCategory

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
class CategoryMapper {

    fun convertList(list: List<WordCategoryEntity>) = list.map(::convert)

    fun convert(wordCategoryEntity: WordCategoryEntity) = WordCategory(
            id = wordCategoryEntity.id ?: -1,
            name = wordCategoryEntity.name,
            isStudy = wordCategoryEntity.isStudy,
            progress = 0
    )

    fun convertToEntity(wordCategory: WordCategory) = WordCategoryEntity(
            id = wordCategory.id,
            name = wordCategory.name,
            isStudy = wordCategory.isStudy,
            createDate = Instant.now()
    )
}