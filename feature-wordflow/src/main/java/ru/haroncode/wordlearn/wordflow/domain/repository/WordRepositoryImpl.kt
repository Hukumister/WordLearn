package ru.haroncode.wordlearn.wordflow.domain.repository

import javax.inject.Inject
import ru.haroncode.wordlearn.common.domain.system.SchedulerProvider
import ru.haroncode.wordlearn.database.dao.WordDao
import ru.haroncode.wordlearn.database.mapper.WordMapper
import ru.haroncode.wordlearn.word.domain.repository.WordRepository
import ru.haroncode.wordlearn.word.model.Word

/**
 * @author HaronCode. Date 21.08.2019.
 */
class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
    private val wordMapper: WordMapper,
    private val schedulerProvider: SchedulerProvider
) : WordRepository {

    override fun findAllPreviewByCategoryId(categoryId: Long) = wordDao.findAllByCategory(categoryId)
            .map(wordMapper::convertListToPreview)
            .subscribeOn(schedulerProvider.io)

    override fun findAllByCategoryId(categoryId: Long) = wordDao.findAllByCategory(categoryId)
            .map(wordMapper::convertList)
            .subscribeOn(schedulerProvider.io)

    override fun save(word: Word) = word
            .let(wordMapper::convertToEntity)
            .let(wordDao::save)
            .subscribeOn(schedulerProvider.io)

    override fun count() = wordDao.count()
            .subscribeOn(schedulerProvider.io)
}
