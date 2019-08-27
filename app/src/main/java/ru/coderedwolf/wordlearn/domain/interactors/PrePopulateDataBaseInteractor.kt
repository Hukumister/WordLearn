package ru.coderedwolf.wordlearn.domain.interactors

import ru.coderedwolf.wordlearn.domain.repository.PhraseAssetRepository
import ru.coderedwolf.wordlearn.domain.repository.PrePopulatePhraseRepository
import ru.coderedwolf.wordlearn.domain.repository.WordAssetRepository
import ru.coderedwolf.wordlearn.mainflow.domain.repository.WordsCategoryRepository
import ru.coderedwolf.wordlearn.word.domain.repository.WordRepository
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */

interface PrePopulateDataBaseInteractor {
    suspend fun shouldInit(): Boolean
    suspend fun prePopulateDataBase()
}

class PrePopulateDataBaseInteractorImpl @Inject constructor(
    private val prePopulatePhraseRepository: PrePopulatePhraseRepository,
    private val phraseAssetRepository: PhraseAssetRepository,
    private val wordsCategoryRepository: WordsCategoryRepository,
    private val wordRepository: WordRepository,
    private val wordAssetRepository: WordAssetRepository
) : PrePopulateDataBaseInteractor {

    override suspend fun shouldInit(): Boolean =
        prePopulatePhraseRepository.phraseCount() == 0 || wordRepository.count() == 0

    override suspend fun prePopulateDataBase() {
        prepopulatePhrase()
        prepopulateWords()
    }

    private suspend fun prepopulateWords() {
        val wordCategory = wordsCategoryRepository.save(
            ru.coderedwolf.wordlearn.wordscategory.model.WordCategory(
                name = "Предзаполненые",
                isStudy = true
            )
        )
        val words = wordAssetRepository.findAllFor(wordCategory.id!!)
        wordRepository.saveAll(words)
    }

    private suspend fun prepopulatePhrase() = phraseAssetRepository.findAllGroupByTopic()
        .forEach { (topic, phraseList) ->
            val savedTopic = prePopulatePhraseRepository.saveTopic(topic)
            prePopulatePhraseRepository.saveAllPhrases(phraseList.map { it.copy(topicId = savedTopic.id!!) })
        }
}