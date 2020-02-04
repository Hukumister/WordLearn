package ru.haroncode.wordlearn.database.mapper

import ru.haroncode.wordlearn.database.entity.PhraseTopicEntity
import ru.haroncode.wordlearn.phrase.model.PhraseTopic

/**
 * @author HaronCode.
 */
class PhraseTopicMapper {

    fun convertList(list: List<PhraseTopicEntity>) = list.map(::convert)

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
