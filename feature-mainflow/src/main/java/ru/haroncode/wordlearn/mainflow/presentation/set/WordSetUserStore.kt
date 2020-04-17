package ru.haroncode.wordlearn.mainflow.presentation.set

import com.haroncode.gemini.core.elements.Bootstrapper
import com.haroncode.gemini.core.elements.EventProducer
import com.haroncode.gemini.core.elements.Middleware
import com.haroncode.gemini.core.elements.Reducer
import com.haroncode.gemini.store.OnlyActionStore
import io.reactivex.Flowable
import javax.inject.Inject
import ru.haroncode.api.wordset.domain.repository.WordSetRepository
import ru.haroncode.api.wordset.model.WordSet
import ru.haroncode.wordlearn.common.di.PerFragment
import ru.haroncode.wordlearn.common.domain.model.Product
import ru.haroncode.wordlearn.common.domain.model.asProduct
import ru.haroncode.wordlearn.common.domain.model.map
import ru.haroncode.wordlearn.common.util.asFlowable
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserStore.Action
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserStore.ViewEvent

/**
 * @author HaronCode.
 */
@PerFragment
class WordSetUserStore @Inject constructor(
    wordSetRepository: WordSetRepository
) : OnlyActionStore<Action, WordSetViewState, ViewEvent>(
    initialState = WordSetViewState(),
    bootstrapper = BootstrapperImpl(),
    reducer = ReducerImpl(),
    eventProducer = ViewEventProducerImpl(),
    middleware = MiddleWareImpl(wordSetRepository)
) {

    sealed class Action {

        object CreateNew : Action()
        object LoadList : Action()

        data class LoadListResult(val list: Product<List<WordSet>>) : Action()
    }

    sealed class ViewEvent {
        object CreateNewDialog : ViewEvent()
    }

    private class ReducerImpl : Reducer<WordSetViewState, Action> {

        override fun invoke(state: WordSetViewState, action: Action): WordSetViewState = when (action) {
            is Action.LoadListResult -> state.copy(items = action.list.map(WordSetFactory::item))
            else -> state
        }
    }

    private class ViewEventProducerImpl : EventProducer<WordSetViewState, Action, ViewEvent> {

        override fun invoke(state: WordSetViewState, action: Action): ViewEvent? = when (action) {
            is Action.CreateNew -> ViewEvent.CreateNewDialog
            else -> null
        }
    }

    private class BootstrapperImpl : Bootstrapper<Action> {
        override fun invoke(): Flowable<Action> = Action.LoadList.asFlowable()
    }

    //region MiddleWare
    private class MiddleWareImpl(
        private val wordSetRepository: WordSetRepository
    ) : Middleware<Action, WordSetViewState, Action> {

        override fun invoke(action: Action, state: WordSetViewState): Flowable<Action> = when (action) {
            is Action.LoadList -> wordSetRepository.observableAllUserSet()
                .asProduct()
                .map(Action::LoadListResult)
            else -> action.asFlowable()
        }
    }
    //endregion
}
