package ru.coderedwolf.wordlearn.wordflow.presentation

import ru.coderedwolf.wordlearn.common.domain.result.Determinate
import ru.coderedwolf.wordlearn.common.domain.validator.NotValid
import ru.coderedwolf.wordlearn.common.domain.validator.VerifiableValue
import ru.coderedwolf.wordlearn.word.model.WordExample

/**
 * @author HaronCode.
 */
data class CreateWordViewState(
    val categoryId: Long,
    val determinate: Determinate = Determinate.Completed,
    val word: VerifiableValue<String> = VerifiableValue("", NotValid),
    val translation: VerifiableValue<String> = VerifiableValue("", NotValid),
    val association: String = "",
    val transcription: String = "",
    val exampleListItem: List<Item> = listOf(Item.AddButtonItem)
) {

    sealed class Item {

        data class WordExampleItem(val wordExample: WordExample) : Item()
        object AddButtonItem : Item()
    }
}