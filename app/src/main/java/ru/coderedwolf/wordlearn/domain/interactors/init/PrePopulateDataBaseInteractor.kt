package ru.coderedwolf.wordlearn.domain.interactors.init

import ru.coderedwolf.wordlearn.domain.reporitory.PhraseAssetRepository
import ru.coderedwolf.wordlearn.domain.reporitory.PrePopulatePhraseRepository
import toothpick.Lazy
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */

interface PrePopulateDataBaseInteractor {

    suspend fun fullDataBase()
}

class PrePopulateDataBaseInteractorImpl @Inject constructor(
        private val prePopulatePhraseRepository: Lazy<PrePopulatePhraseRepository>,
        private val phraseAssetRepository: PhraseAssetRepository
) : PrePopulateDataBaseInteractor {

    override suspend fun fullDataBase() {
        fullPhrases()
    }

    private suspend fun fullPhrases() {
        phraseAssetRepository.findAllGroupByTopic().forEach { (topic, phraseList) ->
            val savedTopic = prePopulatePhraseRepository.get()?.saveTopic(topic) ?: return@forEach
            prePopulatePhraseRepository.get()?.saveAllPhrases(phraseList.map { it.copy(topicId = savedTopic.id!!) })
        }
    }
}