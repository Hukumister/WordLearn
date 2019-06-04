package ru.coderedwolf.wordlearn.presentation.word

import ru.coderedwolf.wordlearn.base.BasePresenter
import ru.coderedwolf.wordlearn.di.PrimitiveWrapper
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 04.06.2019.
 */
class WordListPresenter @Inject constructor(
        categoryIdWrapper: PrimitiveWrapper<Long>,
        categoryNameWrapper: PrimitiveWrapper<String>
) : BasePresenter<WordListView>() {

    private val categorId: Long = categoryIdWrapper.value
    private val categorName: String = categoryNameWrapper.value




}