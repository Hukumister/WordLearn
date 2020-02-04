package ru.haroncode.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.haroncode.mvi.core.BaseStore
import ru.haroncode.mvi.core.MviView
import ru.haroncode.mvi.core.Store
import ru.haroncode.mvi.core.elements.*

/**
 * @author HaronCode. Date 13.10.2019.
 */
abstract class BaseViewModelStore<Action, State, ViewEvent, Effect>(
    initialState: State,
    reducer: Reducer<State, Effect>,
    singleScheduler: Scheduler = Schedulers.single(),
    mainScheduler: Scheduler = AndroidSchedulers.mainThread(),
    middleware: Middleware<Action, State, Effect>,
    bootstrapper: Bootstrapper<Action>? = null,
    eventProducer: EventProducer<State, Effect, ViewEvent>? = null,
    navigator: Navigator<State, Effect>? = null
) : ViewModel(), Store<Action, State, ViewEvent> {

    private val storeDelegate = BaseStore(
        initialState = initialState,
        bootstrapper = bootstrapper,
        reducerScheduler = singleScheduler,
        mainScheduler = mainScheduler,
        reducer = reducer,
        middleware = middleware,
        eventProducer = eventProducer,
        navigator = navigator
    ).apply { create() }

    @CallSuper
    override fun bindView(mviView: MviView<Action, State, ViewEvent>) = storeDelegate.bindView(mviView)

    @CallSuper
    override fun unbindView() = storeDelegate.unbindView()

    override fun onCleared() = storeDelegate.destroy()

}