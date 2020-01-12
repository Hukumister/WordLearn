package ru.coderedwolf.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.coderedwolf.mvi.core.MviView
import ru.coderedwolf.mvi.core.Store
import ru.coderedwolf.mvi.core.elements.*
import ru.coderedwolf.mvi.core.impl.BaseStore
import ru.coderedwolf.mvi.core.schedule.SchedulerProvider

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
abstract class BaseViewModelStore<Action, State, ViewEvent, Effect>(
    initialState: State,
    reducer: Reducer<State, Effect>,
    singleScheduler: SchedulerProvider = { Schedulers.single() },
    mainScheduler: SchedulerProvider = { AndroidSchedulers.mainThread() },
    middleware: Middleware<Action, State, Effect>,
    bootstrapper: Bootstrapper<Action>? = null,
    viewEventProducer: ViewEventProducer<State, Effect, ViewEvent>? = null,
    navigator: Navigator<State, Effect>? = null
) : ViewModel(), Store<Action, State, ViewEvent> {

    private val storeDelegate = BaseStore(
        initialState = initialState,
        bootstrapper = bootstrapper,
        singleScheduler = singleScheduler,
        mainScheduler = mainScheduler,
        reducer = reducer,
        middleware = middleware,
        viewEventProducer = viewEventProducer,
        navigator = navigator
    ).also { store -> store.initStore() }

    @CallSuper
    override fun bindView(mviView: MviView<Action, State, ViewEvent>) = storeDelegate.bindView(mviView)

    @CallSuper
    override fun unbindView() = storeDelegate.unbindView()

    override fun onCleared() = storeDelegate.destroyStore()

}