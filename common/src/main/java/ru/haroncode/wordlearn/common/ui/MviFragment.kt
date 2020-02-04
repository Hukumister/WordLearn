package ru.haroncode.wordlearn.common.ui

import androidx.annotation.LayoutRes
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.haroncode.mvi.core.MviView
import ru.haroncode.mvi.core.Store

/**
 * @author HaronCode.
 */
abstract class MviFragment<Action, State, ViewEvent> @JvmOverloads constructor(
    @LayoutRes layoutRes: Int = 0
) : BaseFragment(layoutRes), MviView<Action, State, ViewEvent> {

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