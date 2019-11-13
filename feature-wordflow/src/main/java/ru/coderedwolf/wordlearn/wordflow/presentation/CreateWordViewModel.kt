package ru.coderedwolf.wordlearn.wordflow.presentation

import ru.coderedwolf.wordlearn.common.domain.validator.Verifiable
import ru.coderedwolf.wordlearn.word.model.WordExample

/**
 * @author CodeRedWolf. Date 24.08.2019.
 */
data class CreateWordViewModel(
    val wordVerify: Verifiable,
    val translationVerify: Verifiable,
    val enableButtonApply: Boolean,
    val exampleList: List<Item>
) {
    sealed class Item {

        data class WordExampleItem(val wordExample: WordExample) : Item()

        object AddItem : Item()

    }

}