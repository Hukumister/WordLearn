package ru.coderedwolf.wordlearn.wordflow.ui

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.using
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import ru.coderedwolf.wordlearn.common.presentation.ErrorHandler
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordFeature
import ru.coderedwolf.wordlearn.word.model.WordExample
import ru.coderedwolf.wordlearn.wordflow.R
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordViewModel
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 24.08.2019.
 */
class CreateWordFragmentBindings @Inject constructor(
        view: CreateWordFragment,
        private val errorHandler: ErrorHandler,
        private val createWordFeature: CreateWordFeature
) : AndroidBindings<CreateWordFragment>(view) {

    override fun setup(view: CreateWordFragment) {
        binder.bind(createWordFeature to view using ViewModelTransformer(errorHandler))
        binder.bind(view to createWordFeature using UiEventTransformer())
    }

}

class ViewModelTransformer(private val errorHandler: ErrorHandler) : (CreateWordFeature.State) -> CreateWordViewModel {

    override fun invoke(state: CreateWordFeature.State): CreateWordViewModel = CreateWordViewModel(
            enableButtonApply = state.word.isValid && state.translation.isValid,
            wordVerify = state.word.verifiable,
            translationVerify = state.translation.verifiable,
            exampleList = state.exampleList,
            error = state.throwable?.let(errorHandler::proceed)
    )

}

class UiEventTransformer : (UiEvent) -> CreateWordFeature.Wish {

    override fun invoke(uiEvent: UiEvent): CreateWordFeature.Wish = when (uiEvent) {
        is UiEvent.ApplyClicked -> CreateWordFeature.Wish.SaveWord
        is UiEvent.AddWordExample -> CreateWordFeature.Wish.AddExample(uiEvent.wordExample)
        is UiEvent.RemoveWordExample -> CreateWordFeature.Wish.RemoveExample(uiEvent.wordExample)
        is UiEvent.ChangeText -> when (uiEvent.textViewTextChangeEvent.view().id) {
            R.id.word -> CreateWordFeature.Wish.ChangeWord(uiEvent.textViewTextChangeEvent.text())
            R.id.transcription -> CreateWordFeature.Wish.ChangeTranscription(uiEvent.textViewTextChangeEvent.text())
            R.id.translation -> CreateWordFeature.Wish.ChangeTranslation(uiEvent.textViewTextChangeEvent.text())
            R.id.association -> CreateWordFeature.Wish.ChangeAssociation(uiEvent.textViewTextChangeEvent.text())
            else -> throw IllegalArgumentException("Unknown edit text id")
        }
    }

}


sealed class UiEvent {

    /**
     * ToDo Should to move in common UiEvents
     */
    object ApplyClicked : UiEvent()

    class ChangeText(val textViewTextChangeEvent: TextViewTextChangeEvent) : UiEvent()

    class AddWordExample(val wordExample: WordExample) : UiEvent()
    class RemoveWordExample(val wordExample: WordExample) : UiEvent()
}