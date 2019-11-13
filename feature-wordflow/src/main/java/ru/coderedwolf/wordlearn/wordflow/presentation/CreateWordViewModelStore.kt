package ru.coderedwolf.wordlearn.wordflow.presentation

import io.reactivex.Observable
import ru.coderedwolf.mvi.core.Middleware
import ru.coderedwolf.mvi.core.Navigator
import ru.coderedwolf.mvi.core.OnlyActionViewModelStore
import ru.coderedwolf.mvi.core.Reducer
import ru.coderedwolf.wordlearn.common.domain.result.Determinate
import ru.coderedwolf.wordlearn.common.domain.result.asDeterminate
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.common.domain.validator.VerifiableValue
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.coderedwolf.wordlearn.word.domain.repository.WordRepository
import ru.coderedwolf.wordlearn.word.model.Word
import ru.coderedwolf.wordlearn.word.model.WordExample
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordViewModelStore.Action
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordViewState.Item
import javax.inject.Inject

class CreateWordViewModelStore @Inject constructor(
    categoryId: Long,
    router: FlowRouter,
    schedulerProvider: SchedulerProvider,
    wordRepository: WordRepository
) : OnlyActionViewModelStore<Action, CreateWordViewState, Nothing>(
    initialState = CreateWordViewState(categoryId),
    schedulerProvider = schedulerProvider,
    navigator = NavigatorImpl(router),
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

    class MiddleWareImpl(
        private val wordRepository: WordRepository
    ) : Middleware<Action, CreateWordViewState, Action> {

        override fun invoke(action: Action, state: CreateWordViewState): Observable<Action> = when (action) {
            is Action.SaveWord -> createWord(state)
                .flatMapCompletable(wordRepository::save)
                .asDeterminate()
                .map(Action::SaveWordResult)
            else -> Observable.just(action)
        }

        private fun createWord(state: CreateWordViewState): Observable<Word> {
            val word = Word(
                word = state.word.value,
                categoryId = state.categoryId,
                association = state.association,
                translation = state.translation.value,
                examplesList = state.exampleListItem
                    .filterIsInstance<Item.WordExampleItem>()
                    .map(Item.WordExampleItem::wordExample)
            )
            return Observable.just(word)
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

    class NavigatorImpl(private val flowRouter: FlowRouter) : Navigator<CreateWordViewState, Action> {

        override fun invoke(state: CreateWordViewState, action: Action) = when (action) {
            is Action.SaveWordResult -> when (action.determinate) {
                is Determinate.Completed -> flowRouter.exit()
                else -> Unit
            }
            else -> Unit
        }

    }

}