package ru.coderedwolf.mvi.core

import io.reactivex.Observable
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider

/**
 * @author CodeRedWolf. Date 14.10.2019.
 */
abstract class ReducerViewModelStore<Action, State, ViewEvent>(
    initialState: State,
    reducer: Reducer<State, Action>,
    schedulerProvider: SchedulerProvider,
    viewEventProducer: ViewEventProducer<State, Action, ViewEvent>? = null,
    navigator: Navigator<State, Action>? = null,
    bootstrapper: Bootstrapper<Action>? = null
) : BaseViewModelStore<Action, State, Action, ViewEvent>(
    initialState = initialState,
    reducer = reducer,
    schedulerProvider = schedulerProvider,
    middleware = BypassMiddleware<State, Action>(),
    navigator = navigator,
    viewEventProducer = viewEventProducer,
    bootstrapper = bootstrapper
) {

    class BypassMiddleware<State, Action> : Middleware<Action, State, Action> {

        override fun invoke(action: Action, state: State): Observable<Action> =
            Observable.just(action)
    }
}