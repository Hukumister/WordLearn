package ru.coderedwolf.wordlearn.presentation.common

import ru.coderedwolf.wordlearn.model.Word
import ru.coderedwolf.wordlearn.model.WordExample

/**
 * @author CodeRedWolf. Date 07.06.2019.
 */
class WordBuilder(
        private var word: String = "",
        private var transcription: String = "",
        private var translation: String = "",
        private var exampleSet: MutableSet<WordExample> = mutableSetOf(),
        private var association: String = ""
) {

    fun word(word: String): WordBuilder {
        this.word = word
        return this
    }

    fun translation(translation: String): WordBuilder {
        this.translation = translation
        return this
    }

    fun transcription(transcription: String): WordBuilder {
        this.transcription = transcription
        return this
    }

    fun association(association: String): WordBuilder {
        this.association = association
        return this
    }

    fun exampleList(list: List<WordExample>): WordBuilder {
        exampleSet.clear()
        exampleSet.addAll(list)
        return this
    }

    fun build(categoryId: Long): Word {
        return Word(
                categoryId = categoryId,
                word = word,
                transcription = transcription,
                association = association,
                translation = translation,
                examplesList = exampleSet.toList()
        )
    }
}