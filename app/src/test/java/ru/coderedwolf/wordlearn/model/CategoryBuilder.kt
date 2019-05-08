package ru.coderedwolf.wordlearn.model

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
object CategoryBuilder {

    fun create(id: Long): Category {
        return Category(
                id = id,
                name = "Test",
                image = "test_image_path",
                progress = 50
        )
    }

    fun createList(): List<Category> {
        return listOf(
                create(1),
                create(2),
                create(3)
        )
    }
}