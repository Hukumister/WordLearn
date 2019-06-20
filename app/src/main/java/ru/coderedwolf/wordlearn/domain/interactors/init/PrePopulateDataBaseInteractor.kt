package ru.coderedwolf.wordlearn.domain.interactors.init

import ru.coderedwolf.wordlearn.domain.reporitory.PhraseAssetRepository
import ru.coderedwolf.wordlearn.domain.reporitory.PrePopulatePhraseRepository
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
        private val phraseAssetRepository: PhraseAssetRepository
) : PrePopulateDataBaseInteractor {

    override suspend fun shouldInit(): Boolean = prePopulatePhraseRepository.phraseCount() == 0

    override suspend fun prePopulateDataBase() = phraseAssetRepository
            .findAllGroupByTopic()
            .forEach { (topic, phraseList) ->
                val savedTopic = prePopulatePhraseRepository.saveTopic(topic)
                prePopulatePhraseRepository.saveAllPhrases(phraseList.map { it.copy(topicId = savedTopic.id!!) })
            }
}