package ru.coderedwolf.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.processors.PublishProcessor
import org.reactivestreams.Subscriber
import ru.coderedwolf.mvi.core.StoreView
import ru.coderedwolf.viewmodel.ViewModelBinder

abstract class MviFragment<Action : Any, State : Any>(
    @LayoutRes layoutRes: Int
) : Fragment(layoutRes), StoreView<Action, State> {

    private val source = PublishProcessor.create<Action>()

    abstract val viewModelBinder: ViewModelBinder<Action, *, State, *>

    override fun onStart() {
        super.onStart()
        viewModelBinder.bindView(this)
    }

    override fun onStop() {
        super.onStop()
        viewModelBinder.unbindView()
    }

    abstract fun onViewStateChanged(state: State)

    fun postAction(action: Action) = source.onNext(action)

    final override fun accept(state: State) = onViewStateChanged(state)

    final override fun subscribe(subscriber: Subscriber<in Action>) = source.subscribe(source)

}