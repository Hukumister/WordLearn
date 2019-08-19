package ru.coderedwolf.wordlearn.model.learn

import ru.coderedwolf.wordlearn.model.word.Word

/**
 * @author CodeRedWolf. Date 22.06.2019.
 */
data class LearnWord(
        val word: Word,
        val categoryName: String,
        var isNewWord: Boolean = true,
        var isRotate: Boolean = false
)