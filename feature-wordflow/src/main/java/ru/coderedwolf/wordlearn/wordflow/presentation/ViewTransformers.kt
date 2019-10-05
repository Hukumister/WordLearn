package ru.coderedwolf.wordlearn.wordflow.presentation

import ru.coderedwolf.wordlearn.common.ui.event.ChangeText
import ru.coderedwolf.wordlearn.common.ui.event.UiEvent
import ru.coderedwolf.wordlearn.word.model.WordExample
import ru.coderedwolf.wordlearn.wordflow.R

/**
 * @author CodeRedWolf. Date 22.09.2019.
 */
class ViewModelTransformer : (CreateWordFeature.State) -> CreateWordViewModel {

    override fun invoke(state: CreateWordFeature.State): CreateWordViewModel = CreateWordViewModel(
            enableButtonApply = state.word.isValid && state.translation.isValid,
            wordVerify = state.word.verifiable,
            translationVerify = state.translation.verifiable,
            exampleList = state.exampleList
    )

}

class UiEventTransformer : (UiEvent) -> CreateWordFeature.Wish {

    override fun invoke(uiEvent: UiEvent): CreateWordFeature.Wish = when (uiEvent) {
        is SaveClick -> CreateWordFeature.Wish.SaveWord
        is AddWordExample -> CreateWordFeature.Wish.AddExample(uiEvent.wordExample)
        is RemoveWordExample -> CreateWordFeature.Wish.RemoveExample(uiEvent.wordExample)
        is ChangeText -> when (uiEvent.elementId) {
            R.id.word -> CreateWordFeature.Wish.ChangeWord(uiEvent.text)
            R.id.transcription -> CreateWordFeature.Wish.ChangeTranscription(uiEvent.text)
            R.id.translation -> CreateWordFeature.Wish.ChangeTranslation(uiEvent.text)
            R.id.association -> CreateWordFeature.Wish.ChangeAssociation(uiEvent.text)
            else -> throw IllegalArgumentException("Unknown edit text id")
        }
        else -> throw IllegalStateException("Unknown ui event")
    }

}

object SaveClick : UiEvent()
class AddWordExample(val wordExample: WordExample) : UiEvent()
class RemoveWordExample(val wordExample: WordExample) : UiEvent()