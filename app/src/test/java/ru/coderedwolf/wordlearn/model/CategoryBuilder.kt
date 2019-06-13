package ru.coderedwolf.wordlearn.model

import ru.coderedwolf.wordlearn.model.word.WordCategory

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
object CategoryBuilder {

    fun create(id: Long): WordCategory {
        return WordCategory(
            id = id,
            name = "Test",
            isStudy = false,
            progress = 50
        )
    }

    fun createList(): List<WordCategory> {
        return listOf(
                create(1),
                create(2),
                create(3)
        )
    }
}