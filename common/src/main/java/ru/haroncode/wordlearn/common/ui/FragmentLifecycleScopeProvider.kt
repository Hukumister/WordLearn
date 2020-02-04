package ru.haroncode.wordlearn.common.ui


import com.uber.autodispose.ScopeProvider
import io.reactivex.CompletableSource
import io.reactivex.subjects.BehaviorSubject

/**
 * @author HaronCode. Date 21.09.2019.
 */
class FragmentLifecycleScopeProvider : ScopeProvider {

    private val lifecycleSubject = BehaviorSubject.create<Unit>()

    fun onDestroy() = lifecycleSubject.onNext(Unit)

    override fun requestScope(): CompletableSource = lifecycleSubject.hide()
            .ignoreElements()

}