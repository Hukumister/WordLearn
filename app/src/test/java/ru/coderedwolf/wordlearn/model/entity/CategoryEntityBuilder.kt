package ru.coderedwolf.wordlearn.model.entity

import java.util.*

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
object CategoryEntityBuilder {

    fun create(id: Long, createDate: Date = Date()): CategoryEntity {
        return CategoryEntity(
                id = id,
                name = "test",
                createDate = createDate
        )
    }

    fun createList(): List<CategoryEntity> {
        return listOf(
                create(1),
                create(2),
                create(3)
        )
    }
}