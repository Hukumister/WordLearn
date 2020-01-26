package ru.coderedwolf.wordlearn.wordflow.presentation

import io.reactivex.Flowable
import ru.coderedwolf.mvi.elements.EventProducer
import ru.coderedwolf.mvi.elements.Middleware
import ru.coderedwolf.mvi.elements.Reducer
import ru.coderedwolf.mvi.store.OnlyActionStore
import ru.coderedwolf.wordlearn.common.domain.result.Determinate
import ru.coderedwolf.wordlearn.common.domain.result.asDeterminate
import ru.coderedwolf.wordlearn.common.domain.validator.VerifiableValue
import ru.coderedwolf.wordlearn.common.util.SimpleValidator
import ru.coderedwolf.wordlearn.word.domain.repository.WordRepository
import ru.coderedwolf.wordlearn.word.model.Word
import ru.coderedwolf.wordlearn.word.model.WordExample
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordStore.Action
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordStore.Event
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordViewState.Item
import javax.inject.Inject

class CreateWordStore @Inject constructor(
    categoryId: Long,
    wordRepository: WordRepository
) : OnlyActionStore<Action, CreateWordViewState, Event>(
    eventProducer = EventProducerImpl(),
    initialState = CreateWordViewState(categoryId),
    middleware = MiddleWareImpl(wordRepository),
    reducer = ReducerImpl()
) {

    sealed class Action {

        data class ChangeWord(val word: CharSequence) : Action()
        data class ChangeTranslation(val translation: CharSequence) : Action()
        data class ChangeAssociation(val association: CharSequence) : Action()
        data class ChangeTranscription(val transcription: CharSequence) : Action()
        data class AddExample(val example: WordExample) : Action()
        data class RemoveExample(val example: WordExample) : Action()

        data class SaveWordResult(val determinate: Determinate) : Action()
        object SaveWord : Action()
    }

    sealed class Event {

        data class SaveResult(val determinate: Determinate) : Event()
    }

    class MiddleWareImpl(
        private val wordRepository: WordRepository
    ) : Middleware<Action, CreateWordViewState, Action> {

        override fun invoke(action: Action, state: CreateWordViewState): Flowable<Action> = when (action) {
            is Action.SaveWord -> createWord(state)
                .flatMapCompletable(wordRepository::save)
                .asDeterminate()
                .map(Action::SaveWordResult)
            else -> Flowable.just(action)
        }

        private fun createWord(state: CreateWordViewState): Flowable<Word> {
            val word = Word(
                word = state.word.value,
                categoryId = state.categoryId,
                association = state.association,
                translation = state.translation.value,
                examplesList = state.exampleListItem
                    .filterIsInstance<Item.WordExampleItem>()
                    .map(Item.WordExampleItem::wordExample)
            )
            return Flowable.just(word)
        }

    }

    class ReducerImpl : Reducer<CreateWordViewState, Action> {

        override fun invoke(state: CreateWordViewState, action: Action): CreateWordViewState = when (action) {
            is Action.ChangeWord -> {
                val verifiable = action.word.let(SimpleValidator::isNotNullOrEmptyOrBlank)
                state.copy(word = VerifiableValue(action.word.toString(), verifiable))
            }
            is Action.ChangeTranslation -> {
                val verifiable = action.translation.let(SimpleValidator::isNotNullOrEmptyOrBlank)
                state.copy(word = VerifiableValue(action.translation.toString(), verifiable))
            }
            is Action.ChangeAssociation -> {
                val verifiable = action.association.let(SimpleValidator::isNotNullOrEmptyOrBlank)
                state.copy(word = VerifiableValue(action.association.toString(), verifiable))
            }
            is Action.ChangeTranscription -> {
                val verifiable = action.transcription.let(SimpleValidator::isNotNullOrEmptyOrBlank)
                state.copy(word = VerifiableValue(action.transcription.toString(), verifiable))
            }

            is Action.AddExample -> state.copy(exampleListItem = state.exampleListItem + action.example.toItem())
            is Action.RemoveExample -> state.copy(exampleListItem = state.exampleListItem - action.example.toItem())
            is Action.SaveWordResult -> state.copy(determinate = action.determinate)
            else -> state
        }

        private fun WordExample.toItem(): Item.WordExampleItem = Item.WordExampleItem(this)

    }

    class EventProducerImpl : EventProducer<CreateWordViewState, Action, Event> {

        override fun invoke(state: CreateWordViewState, action: Action) = when (action) {
            is Action.SaveWordResult -> Event.SaveResult(action.determinate)
            else -> null
        }

    }

}