package ru.coderedwolf.wordlearn.domain.reporitory

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.domain.data.DataBase
import ru.coderedwolf.wordlearn.domain.mappers.PhraseTopicMapper
import ru.coderedwolf.wordlearn.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.model.phrase.PhraseTopic
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
interface PhraseTopicRepository {

    suspend fun save(phraseTopic: PhraseTopic): PhraseTopic
}

class PhraseTopicRepositoryImpl @Inject constructor(
        dataBase: DataBase,
        private val phraseTopicMapper: PhraseTopicMapper,
        private val dispatchersProvider: DispatchersProvider
) : PhraseTopicRepository {

    private val phraseTopicDao = dataBase.phraseTopicDao()

    override suspend fun save(phraseTopic: PhraseTopic): PhraseTopic = withContext(dispatchersProvider.io()) {
        val entity = phraseTopicMapper.convertToEntity(phraseTopic)
        phraseTopicMapper.convert(phraseTopicDao.saveAndReturn(entity))
    }
}