package ru.coderedwolf.mvi.core

import io.reactivex.Observable
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider

/**
 * @author CodeRedWolf. Date 14.10.2019.
 */
abstract class ReducerViewModelStore<Action, State, Event>(
    initialState: State,
    reducer: Reducer<State, Action>,
    schedulerProvider: SchedulerProvider,
    navigator: Navigator<State, Action, Event>? = null,
    bootstrapper: Bootstrapper<Action>? = null
) : BaseViewModelStore<Action, State, Action, Event>(
    initialState = initialState,
    reducer = reducer,
    schedulerProvider = schedulerProvider,
    middleWare = BypassMiddleWare<State, Action>(),
    navigator = navigator,
    bootstrapper = bootstrapper
) {

    class BypassMiddleWare<State, Action> : MiddleWare<Action, State, Action> {

        override fun handle(action: Action, state: State): Observable<Action> =
            Observable.just(action)

    }

}