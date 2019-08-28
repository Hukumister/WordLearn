package ru.coderedwolf.wordlearn.database.mapper

import ru.coderedwolf.wordlearn.database.entity.PhraseTopicEntity
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
class PhraseTopicMapper {

    fun convert(phraseTopicEntity: PhraseTopicEntity) = PhraseTopic(
        id = phraseTopicEntity.id,
        isStudy = phraseTopicEntity.isStudy,
        title = phraseTopicEntity.title
    )

    fun convertToEntity(phraseTopic: PhraseTopic) = PhraseTopicEntity(
        id = phraseTopic.id,
        isStudy = phraseTopic.isStudy,
        title = phraseTopic.title
    )
}