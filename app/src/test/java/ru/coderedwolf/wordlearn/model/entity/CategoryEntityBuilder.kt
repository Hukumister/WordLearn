package ru.coderedwolf.wordlearn.model.entity

import ru.coderedwolf.wordlearn.database.entity.WordCategoryEntity
import java.util.*

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
object CategoryEntityBuilder {

    fun create(id: Long, createDate: Date = Date()): WordCategoryEntity {
        return WordCategoryEntity(
            id = id,
            name = "test",
            createDate = createDate,
            isStudy = false
        )
    }

    fun createList(): List<WordCategoryEntity> {
        return listOf(
                create(1),
                create(2),
                create(3)
        )
    }
}