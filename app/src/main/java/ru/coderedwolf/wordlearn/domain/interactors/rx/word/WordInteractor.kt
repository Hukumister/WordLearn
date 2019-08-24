package ru.coderedwolf.wordlearn.domain.interactors.rx.word

import io.reactivex.Completable
import io.reactivex.Single
import ru.coderedwolf.wordlearn.domain.repository.rx.RxWordRepository
import ru.coderedwolf.wordlearn.model.word.Word
import ru.coderedwolf.wordlearn.model.word.WordPreview
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 05.06.2019.
 */
interface RxWordInteractor {

    fun findAllPreviewByCategoryId(categoryId: Long): Single<List<WordPreview>>

    fun findAllByCategoryId(categoryId: Long): Single<List<Word>>

    fun saveWord(word: Word): Completable

}

class RxWordInteractorImpl @Inject constructor(
        private val wordRepository: RxWordRepository
) : RxWordInteractor {

    override fun findAllPreviewByCategoryId(categoryId: Long) = wordRepository
            .findAllPreviewByCategoryId(categoryId)

    override fun findAllByCategoryId(categoryId: Long) = wordRepository
            .findAllByCategoryId(categoryId)

    override fun saveWord(word: Word) = wordRepository.save(word)
}
