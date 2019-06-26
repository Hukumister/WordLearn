package ru.coderedwolf.wordlearn.domain.mappers

import ru.coderedwolf.wordlearn.model.entity.PhraseTopicEntity
import ru.coderedwolf.wordlearn.model.phrase.PhraseTopic
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
class PhraseTopicMapper @Inject constructor() {

    fun convert(phraseTopicEntity: PhraseTopicEntity): PhraseTopic {
        return PhraseTopic(
                id = phraseTopicEntity.id,
                isStudy = phraseTopicEntity.isStudy,
                title = phraseTopicEntity.title
        )
    }

    fun convertToEntity(phraseTopic: PhraseTopic): PhraseTopicEntity {
        return PhraseTopicEntity(
                id = phraseTopic.id,
                isStudy = phraseTopic.isStudy,
                title = phraseTopic.title
        )
    }
}