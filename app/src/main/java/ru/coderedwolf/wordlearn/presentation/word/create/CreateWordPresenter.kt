package ru.coderedwolf.wordlearn.presentation.word.create

import moxy.InjectViewState
import ru.coderedwolf.wordlearn.di.FlowRouter
import ru.coderedwolf.wordlearn.di.PrimitiveWrapper
import ru.coderedwolf.wordlearn.di.provider.qualifier.CategoryId
import ru.coderedwolf.wordlearn.domain.interactors.word.WordInteractor
import ru.coderedwolf.wordlearn.exception.ViolationException
import ru.coderedwolf.wordlearn.model.word.WordExample
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

    private val exampleList: MutableList<WordExample> = mutableListOf()

    private val categoryId: Long = categoryIdWrapper.value
    private val wordBuilder = WordBuilder()

    fun onAddWordExample(example: String, translation: String) {
        val wordExample = WordExample(example, translation)
        val result = exampleList.add(wordExample)
        if (result) {
            viewState.updateExampleList(exampleList)
        }
    }

    fun onRemoveExample(example: WordExample) {
        exampleList.remove(example)
        viewState.updateExampleList(exampleList)
    }

    fun onClickAddExample() = viewState.showDialogCreateExample()

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
                .exampleList(exampleList)
                .transcription(transcription)
                .build(categoryId)

        try {
            wordInteractor.addWord(addedWord)
            router.exit()
        } catch (ex: Throwable) {
            handleException(ex)
        }
    }

    override fun onBackPressed() = router.exit()

    private fun handleException(throwable: Throwable) {
        if (throwable is ViolationException) {
            viewState.showFieldError(throwable.violation)
        } else {
            viewState.showError(errorHandler.proceed(throwable))
        }
    }
}
