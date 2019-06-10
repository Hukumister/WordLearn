package ru.coderedwolf.wordlearn.presentation.word.create

import moxy.InjectViewState
import ru.coderedwolf.wordlearn.di.FlowRouter
import ru.coderedwolf.wordlearn.di.PrimitiveWrapper
import ru.coderedwolf.wordlearn.di.provider.qualifier.CategoryId
import ru.coderedwolf.wordlearn.domain.interactors.word.WordInteractor
import ru.coderedwolf.wordlearn.exception.ViolationException
import ru.coderedwolf.wordlearn.model.WordExample
import ru.coderedwolf.wordlearn.presentation.base.BasePresenter
import ru.coderedwolf.wordlearn.presentation.common.WordBuilder
import ru.coderedwolf.wordlearn.presentation.global.ErrorHandler
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
@InjectViewState
class CreateWordPresenter @Inject constructor(
        @CategoryId categoryIdWrapper: PrimitiveWrapper<Long>,
        private val router: FlowRouter,
        private val wordInteractor: WordInteractor,
        private val errorHandler: ErrorHandler
) : BasePresenter<CreateWordView>() {

    private val categoryId: Long = categoryIdWrapper.value
    private val wordBuilder = WordBuilder()

    fun onAddWordExample(position: Int, example: String, translation: String) {
        wordBuilder.addExample(position, WordExample(example, translation))
    }

    fun onRemoveExample(position: Int) {
        wordBuilder.removeExample(position)
        viewState.removeExample(position)
    }

    fun onClickAddExample() = viewState.addExampleView()

    fun onClickSave(
            word: String,
            transcription: String,
            translation: String,
            association: String
    ) = launchUI {

        val addedWord = wordBuilder
                .word(word)
                .translation(translation)
                .association(association)
                .transcription(transcription)
                .build(categoryId)

        try {
            wordInteractor.addWord(addedWord)
        } catch (ex: Throwable) {
            handleException(ex)
        }
    }

    override fun onBackPressed() = router.finishFlow()

    private fun handleException(throwable: Throwable) {
        if (throwable is ViolationException) {
            viewState.showFieldError(throwable.violation)
        } else {
            viewState.showError(errorHandler.proceed(throwable))
        }
    }
}
