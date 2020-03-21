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
import ru.haroncode.wordlearn.common.domain.result.Product
import ru.haroncode.wordlearn.common.domain.result.asProduct
import ru.haroncode.wordlearn.common.domain.result.map
import ru.haroncode.wordlearn.common.presentation.FlowRouter
import ru.haroncode.wordlearn.common.util.asFlowable
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserStore.Action
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserStore.ViewEvent

/**
 * @author HaronCode.
 */
@PerFragment
class WordSetUserStore @Inject constructor(
    wordSetRepository: WordSetRepository,
    router: FlowRouter
) : OnlyActionStore<Action, WordSetUserViewState, ViewEvent>(
    initialState = WordSetUserViewState(),
    bootstrapper = BootstrapperImpl(),
    reducer = ReducerImpl(),
    eventProducer = ViewEventProducerImpl(),
    middleware = MiddleWareImpl(wordSetRepository)
) {

    sealed class Action {

        object Back : Action()
        object CreateNew : Action()
        object LoadList : Action()
        data class WordSetClick(val id: Long) : Action()

        data class LoadListResult(val list: Product<List<WordSet>>) : Action()
    }

    sealed class ViewEvent {
        object CreateNewDialog : ViewEvent()
    }

    private class ReducerImpl : Reducer<WordSetUserViewState, Action> {

        override fun invoke(state: WordSetUserViewState, action: Action): WordSetUserViewState = when (action) {
            is Action.LoadListResult -> {
                val items = action.list.map(WordSetUserFactory::item)
                state.copy(items = items)
            }
            else -> state
        }
    }
//
//    private class NavigatorImpl(private val router: FlowRouter) : Navigator<WordSetUserViewState, Action> {
//
//        override fun invoke(state: WordSetUserViewState, action: Action) = when (action) {
//            is Action.WordSetClick -> router.navigateTo(MainFlowScreens.WordSet)
//            is Action.Back -> router.exit()
//            else -> Unit
//        }
//    }

    private class ViewEventProducerImpl : EventProducer<WordSetUserViewState, Action, ViewEvent> {

        override fun invoke(state: WordSetUserViewState, action: Action): ViewEvent? = when (action) {
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
    ) : Middleware<Action, WordSetUserViewState, Action> {

        override fun invoke(action: Action, state: WordSetUserViewState): Flowable<Action> = when (action) {
            is Action.LoadList -> wordSetRepository.observableAllUserSet()
                .asProduct()
                .map(Action::LoadListResult)
            else -> action.asFlowable()
        }
    }
    //endregion
}
