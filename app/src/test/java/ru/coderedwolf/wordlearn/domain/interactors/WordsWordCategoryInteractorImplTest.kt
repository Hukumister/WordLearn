package ru.coderedwolf.wordlearn.domain.interactors

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.OverrideMockKs
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.coderedwolf.wordlearn.domain.interactors.word.category.WordsCategoryInteractorImpl
import ru.coderedwolf.wordlearn.domain.reporitory.WordsCategoryRepository
import ru.coderedwolf.wordlearn.model.CategoryBuilder

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
class WordsCategoryInteractorImplTest {

    @MockK lateinit var repository: WordsCategoryRepository

    @OverrideMockKs lateinit var interactor: WordsCategoryInteractorImpl

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun findAllCategory() = runBlocking {
        coEvery { repository.findAll() } returns CategoryBuilder.createList()

        val list = interactor.findAllCategory()

        Assert.assertEquals(list.size, 3)
    }
}