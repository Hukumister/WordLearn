package ru.coderedwolf.wordlearn.domain.mappers

import ru.coderedwolf.wordlearn.model.Category
import ru.coderedwolf.wordlearn.model.entity.CategoryEntity
import java.util.*

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
class CategoryMapper {

    fun convert(categoryEntity: CategoryEntity): Category {
        return Category(
                id = categoryEntity.id ?: -1,
                name = categoryEntity.name,
                image = categoryEntity.image,
                progress = 0
        )
    }

    fun convertToEntity(category: Category): CategoryEntity {
        return CategoryEntity(
                id = category.id,
                name = category.name,
                image = category.image,
                createDate = Date()
        )
    }
}