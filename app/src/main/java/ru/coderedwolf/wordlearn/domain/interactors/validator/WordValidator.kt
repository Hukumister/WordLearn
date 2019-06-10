package ru.coderedwolf.wordlearn.domain.interactors.validator

import ru.coderedwolf.wordlearn.domain.system.ResourceProvider
import ru.coderedwolf.wordlearn.model.Word
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 09.06.2019.
 */
class WordValidator @Inject constructor(
        resourceProvider: ResourceProvider
) : BaseValidator<Word>(resourceProvider) {

    companion object {

        const val WORD_FIELD = "WORD_FIELD"
        const val TRANSLATION_FIELD = "TRANSCRIPTION_FIELD"
    }

    override fun validate(original: Word): Violation {
        return Violation.of(
                WORD_FIELD to validForEmpty(original.word),
                TRANSLATION_FIELD to validForEmpty(original.translation)
        )
    }
}