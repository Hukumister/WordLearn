package ru.coderedwolf.wordlearn.domain.interactors.phrase

import ru.coderedwolf.wordlearn.domain.reporitory.PhraseTopicRepository
import ru.coderedwolf.wordlearn.model.phrase.PhraseTopic
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */

interface PhraseTopicInteractor {

    suspend fun findAll(): List<PhraseTopic>

    suspend fun updateStudy(topicId: Long, isStudy: Boolean)
}

class PhraseTopicInteractorImpl @Inject constructor(
        private val phraseTopicRepository: PhraseTopicRepository
) : PhraseTopicInteractor {

    override suspend fun findAll(): List<PhraseTopic> = phraseTopicRepository.findAll()

    override suspend fun updateStudy(topicId: Long, isStudy: Boolean) = phraseTopicRepository
            .updateStudy(topicId, isStudy)

}

