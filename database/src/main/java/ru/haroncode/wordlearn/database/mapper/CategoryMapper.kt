package ru.haroncode.wordlearn.database.mapper

import org.threeten.bp.Instant
import ru.haroncode.wordlearn.database.entity.WordCategoryEntity
import ru.haroncode.wordlearn.wordscategory.model.WordCategory

/**
 * @author HaronCode.
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