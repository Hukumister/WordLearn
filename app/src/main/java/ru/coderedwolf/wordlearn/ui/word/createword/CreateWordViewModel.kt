package ru.coderedwolf.wordlearn.ui.word.createword

import ru.coderedwolf.wordlearn.domain.interactors.input.Verifiable
import ru.coderedwolf.wordlearn.model.word.WordExample

/**
 * @author CodeRedWolf. Date 24.08.2019.
 */
data class CreateWordViewModel(
        val wordVerify: Verifiable,
        val translationVerify: Verifiable,
        val enableButtonApply: Boolean,
        val error: String?,
        val exampleList: List<WordExample>
)