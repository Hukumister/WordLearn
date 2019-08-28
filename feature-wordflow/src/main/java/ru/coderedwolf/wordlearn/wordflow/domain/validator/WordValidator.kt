package ru.coderedwolf.wordlearn.wordflow.domain.validator

import ru.coderedwolf.wordlearn.common.domain.system.ResourceProvider
import ru.coderedwolf.wordlearn.common.domain.validator.BaseValidator
import ru.coderedwolf.wordlearn.common.domain.validator.Violation
import ru.coderedwolf.wordlearn.word.model.Word

/**
 * @author CodeRedWolf. Date 09.06.2019.
 */
class WordValidator(
    resourceProvider: ResourceProvider
) : BaseValidator<Word>(resourceProvider) {

    companion object {
        const val WORD_FIELD = "WORD_FIELD"
        const val TRANSLATION_FIELD = "TRANSCRIPTION_FIELD"
    }

    override fun validate(original: Word) = Violation.of(
        WORD_FIELD to validForEmpty(original.word),
        TRANSLATION_FIELD to validForEmpty(original.translation)
    )
}