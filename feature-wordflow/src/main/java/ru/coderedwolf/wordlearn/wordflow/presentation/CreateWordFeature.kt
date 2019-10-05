package ru.coderedwolf.wordlearn.wordflow.presentation

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import io.reactivex.Observable
import ru.coderedwolf.wordlearn.common.domain.result.Determinate
import ru.coderedwolf.wordlearn.common.domain.result.asDeterminate
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.common.domain.validator.NotValid
import ru.coderedwolf.wordlearn.common.domain.validator.VerifiableValue
import ru.coderedwolf.wordlearn.common.extension.verify
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.coderedwolf.wordlearn.word.domain.repository.RxWordRepository
import ru.coderedwolf.wordlearn.word.model.Word
import ru.coderedwolf.wordlearn.word.model.WordExample
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordFeature.*
import javax.inject.Inject

class CreateWordFeature @Inject constructor(
        categoryId: Long,
        router: FlowRouter,
        schedulerProvider: SchedulerProvider,
        wordInteractor: RxWordRepository
) : ActorReducerFeature<Wish, Effect, State, Unit>(
        initialState = State(categoryId),
        newsPublisher = NewsPublisherImpl(router),
        actor = ActorImpl(schedulerProvider, wordInteractor),
        reducer = ReducerImpl()
) {

    data class State(
            val categoryId: Long,
            val word: VerifiableValue<String> = VerifiableValue("", NotValid),
            val translation: VerifiableValue<String> = VerifiableValue("", NotValid),
            val association: String = "",
            val transcription: String = "",
            val exampleList: List<WordExample> = emptyList()
    )

    sealed class Wish {

        class ChangeWord(val word: CharSequence) : Wish()
        class ChangeTranslation(val translation: CharSequence) : Wish()
        class ChangeAssociation(val association: CharSequence) : Wish()
        class ChangeTranscription(val transcription: CharSequence) : Wish()
        class AddExample(val example: WordExample) : Wish()
        class RemoveExample(val example: WordExample) : Wish()
        object SaveWord : Wish()

    }

    sealed class Effect {

        class HandleWish(val wish: Wish) : Effect()
        class ChangeWord(val word: VerifiableValue<String>) : Effect()
        class ChangeTranslation(val translation: VerifiableValue<String>) : Effect()
        class SaveResult(val determinate: Determinate) : Effect()

    }

    class ActorImpl(
            private val scheduler: SchedulerProvider,
            private val wordInteractor: RxWordRepository
    ) : Actor<State, Wish, Effect> {

        override fun invoke(state: State, wish: Wish): Observable<out Effect> = when (wish) {
            is Wish.ChangeWord -> Observable.just(wish.word)
                    .observeOn(scheduler.computation)
                    .map(CharSequence::toString)
                    .verify(SimpleValidator::isNotNullOrEmptyOrBlank)
                    .map(Effect::ChangeWord)
                    .observeOn(scheduler.mainThread)

            is Wish.ChangeTranslation -> Observable.just(wish.translation)
                    .observeOn(scheduler.computation)
                    .map(CharSequence::toString)
                    .verify(SimpleValidator::isNotNullOrEmptyOrBlank)
                    .map(Effect::ChangeTranslation)
                    .observeOn(scheduler.mainThread)

            is Wish.SaveWord -> createWord(state)
                    .flatMapCompletable(wordInteractor::save)
                    .asDeterminate()
                    .map(Effect::SaveResult)
                    .observeOn(scheduler.mainThread)

            else -> Observable.just(wish).map(Effect::HandleWish)
        }

        private fun createWord(state: State): Observable<Word> {
            val word = Word(
                    word = state.word.value,
                    categoryId = state.categoryId,
                    association = state.association,
                    transcription = state.transcription,
                    translation = state.translation.value,
                    examplesList = state.exampleList
            )
            return Observable.just(word)
        }

    }

    class ReducerImpl : Reducer<State, Effect> {

        override fun invoke(state: State, effect: Effect): State = when (effect) {
            is Effect.ChangeTranslation -> state.copy(translation = effect.translation)
            is Effect.ChangeWord -> state.copy(word = effect.word)
            is Effect.HandleWish -> handleWish(state, effect.wish)
            is Effect.SaveResult -> state
        }

        private fun handleWish(state: State, wish: Wish) = when (wish) {
            is Wish.ChangeAssociation -> state.copy(association = wish.association.toString())
            is Wish.ChangeTranscription -> state.copy(transcription = wish.transcription.toString())
            is Wish.AddExample -> state.copy(exampleList = state.exampleList + wish.example)
            is Wish.RemoveExample -> state.copy(exampleList = state.exampleList - wish.example)
            else -> throw IllegalStateException("Unknown wish for use in reducer")
        }

    }


    class NewsPublisherImpl(private val flowRouter: FlowRouter) : NewsPublisher<Wish, Effect, State, Unit> {
        override fun invoke(wish: Wish, effect: Effect, state: State) = when (effect) {
            is Effect.SaveResult -> when (effect.determinate) {
                is Determinate.Completed -> flowRouter.exit()
                else -> Unit
            }
            else -> Unit
        }
    }

}