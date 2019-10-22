package ru.coderedwolf.wordlearn.mvicore

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import ru.coderedwolf.wordlearn.common.DispatchersProvider
import kotlin.coroutines.CoroutineContext

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseViewModelStore<Action, State, Effect, NavigationEvent>(
    protected val dispatchersProvider: DispatchersProvider,
    initialState: State,
    private val reducer: Reducer<State, Effect>,
    private val middleware: Middleware<Action, State, Effect>,
    private val navigator: Navigator<State, Effect, NavigationEvent>? = null,
    private val bootstrapper: Bootstrapper<Action>? = null
) : ViewModel(),
    Store<Action, State>,
    CoroutineScope {

    override val coroutineContext: CoroutineContext = dispatchersProvider.ui() + SupervisorJob()

    private var viewBind: Job? = null

    private val stateChannel = ConflatedBroadcastChannel(initialState)
    private val actionChannel = BroadcastChannel<Action>(BUFFERED)
    private val effectChannel = BroadcastChannel<Effect>(BUFFERED)
    private val navigationEventChannel = BroadcastChannel<NavigationEvent>(BUFFERED)

    init {
        effectChannel.asFlow()
            .combine(stateChannel.asFlow())
            .map { (effect, state) ->
                reducer.reduce(state, effect)
            }
            .distinctUntilChanged()
            .onEach(stateChannel::send)
            .launchIn(this)

        actionChannel.asFlow()
            .combine(stateChannel.asFlow())
            .flatMapMerge { (action, state) -> middleware.handle(action, state) }
            .onEach(effectChannel::send)
            .launchIn(this)

        navigationEventChannel.asFlow()
            .onEach { navigationEvent -> onNavigationEvent(navigationEvent) }
            .launchIn(this)

        bootstrapper?.invoke()
            ?.onEach(actionChannel::send)
            ?.launchIn(this)
    }

    open fun onNavigationEvent(navigationEvent: NavigationEvent) = Unit

    override fun bindView(mviView: MviView<Action, State>) {
        viewBind = launch {
            stateChannel.asFlow()
                .onEach { state -> mviView.render(state) }
                .launchIn(this)

            mviView.actions
                .onEach(actionChannel::send)
                .launchIn(this)
        }
    }

    override fun unbindView() {
        viewBind?.cancel()
    }

    @CallSuper
    override fun onCleared() = cancel()
}