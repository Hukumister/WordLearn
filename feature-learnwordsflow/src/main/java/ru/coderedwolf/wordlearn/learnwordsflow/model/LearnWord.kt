package ru.coderedwolf.wordlearn.learnwordsflow.model

import ru.coderedwolf.wordlearn.word.model.Word

/**
 * @author CodeRedWolf. Date 22.06.2019.
 */
data class LearnWord(
    val word: Word,
    val categoryName: String,
    var isNewWord: Boolean = true,
    var isRotate: Boolean = false
)