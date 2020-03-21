package ru.haroncode.wordlearn.common.ui

import androidx.annotation.LayoutRes
import com.haroncode.gemini.core.StoreView
import io.reactivex.processors.PublishProcessor
import org.reactivestreams.Subscriber

/**
 * @author HaronCode.
 */
abstract class PublishFragment<Action : Any, State : Any> @JvmOverloads constructor(
    @LayoutRes layoutRes: Int = 0
) : BaseFragment(layoutRes), StoreView<Action, State> {

    private val source = PublishProcessor.create<Action>()

    override fun accept(state: State) = onViewStateChanged(state)

    abstract fun onViewStateChanged(state: State)

    override fun subscribe(subscriber: Subscriber<in Action>) = source.subscribe(subscriber)

    fun postAction(action: Action) = source.onNext(action)
}
