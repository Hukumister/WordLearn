package ru.coderedwolf.wordlearn.common.ui

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.coderedwolf.mvi.core.MviView
import ru.coderedwolf.mvi.core.Store

/**
 * @author CodeRedWolf.
 */
abstract class MviDialogFragment<Action, State, ViewEvent> : BaseDialogFragment(),
    MviView<Action, State, ViewEvent> {

    override val actions: Observable<Action>
        get() = source.hide()

    private val source = PublishSubject.create<Action>()

    abstract val store: Store<Action, State, ViewEvent>

    override fun onStart() {
        super.onStart()
        store.bindView(this)
    }

    override fun onStop() {
        super.onStop()
        store.unbindView()
    }

    fun postAction(action: Action) = source.onNext(action)

}