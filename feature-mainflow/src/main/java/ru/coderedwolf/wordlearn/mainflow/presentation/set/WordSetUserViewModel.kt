package ru.coderedwolf.wordlearn.mainflow.presentation.set

import io.reactivex.Flowable
import io.reactivex.Observable
import ru.coderedwolf.api.wordset.domain.repository.WordSetRepository
import ru.coderedwolf.api.wordset.model.WordSet
import ru.coderedwolf.mvi.core.elements.*
import ru.coderedwolf.viewmodel.OnlyActionViewModelStore
import ru.coderedwolf.wordlearn.common.domain.result.Product
import ru.coderedwolf.wordlearn.common.domain.result.asProduct
import ru.coderedwolf.wordlearn.common.domain.result.map
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.coderedwolf.wordlearn.common.util.asFlowable
import ru.coderedwolf.wordlearn.common.util.asObservable
import ru.coderedwolf.wordlearn.mainflow.presentation.set.WordSetUserViewModel.Action
import ru.coderedwolf.wordlearn.mainflow.presentation.set.WordSetUserViewModel.ViewEvent
import ru.coderedwolf.wordlearn.mainflow.ui.MainFlowScreens
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 11.10.2019.
 */
class WordSetUserViewModel @Inject constructor(
    wordSetRepository: WordSetRepository,
    router: FlowRouter
) : OnlyActionViewModelStore<Action, WordSetUserViewState, ViewEvent>(
    initialState = WordSetUserViewState(),
    bootstrapper = BootstrapperImpl(),
    reducer = ReducerImpl(),
    navigator = NavigatorImpl(router),
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

    private class NavigatorImpl(private val router: FlowRouter) : Navigator<WordSetUserViewState, Action> {

        override fun invoke(state: WordSetUserViewState, action: Action) = when (action) {
            is Action.WordSetClick -> router.navigateTo(MainFlowScreens.WordSet)
            is Action.Back -> router.exit()
            else -> Unit
        }

    }

    private class ViewEventProducerImpl : EventProducer<WordSetUserViewState, Action, ViewEvent> {

        override fun invoke(state: WordSetUserViewState, action: Action): ViewEvent? = when (action) {
            is Action.CreateNew -> ViewEvent.CreateNewDialog
            else -> null
        }

    }

    private class BootstrapperImpl : Bootstrapper<Action> {
        override fun invoke(): Observable<Action> = Action.LoadList.asObservable()
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







