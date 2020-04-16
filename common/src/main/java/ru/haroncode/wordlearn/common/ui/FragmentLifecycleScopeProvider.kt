package ru.haroncode.wordlearn.common.ui

import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * @author HaronCode.
 */
class FragmentLifecycleScopeProvider : LifecycleScopeProvider<FragmentLifecycleScopeProvider.Event> {

    enum class Event {
        ATTACH,
        DETACH,
        START,
        STOP,
    }

    private val correspondingEventsFunction: CorrespondingEventsFunction<Event> = CorrespondingEventsFunction { event ->
        when (event) {
            Event.ATTACH -> Event.DETACH
            Event.START -> Event.STOP
            else -> throw IllegalStateException()
        }
    }

    private val lifecycleSubject = BehaviorSubject.create<Event>()

    fun onAttach() = lifecycleSubject.onNext(Event.ATTACH)

    fun onDetach() = lifecycleSubject.onNext(Event.DETACH)

    fun onStop() = lifecycleSubject.onNext(Event.ATTACH)

    fun onStart() = lifecycleSubject.onNext(Event.DETACH)

    override fun lifecycle(): Observable<Event> = lifecycleSubject.hide()

    override fun correspondingEvents(): CorrespondingEventsFunction<Event> = correspondingEventsFunction

    override fun peekLifecycle(): Event? = lifecycleSubject.value
}
