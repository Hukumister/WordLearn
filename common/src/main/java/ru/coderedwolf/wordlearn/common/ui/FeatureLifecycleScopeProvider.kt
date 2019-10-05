package ru.coderedwolf.wordlearn.common.ui


import com.uber.autodispose.ScopeProvider
import io.reactivex.CompletableSource
import io.reactivex.subjects.BehaviorSubject

/**
 * @author CodeRedWolf. Date 21.09.2019.
 */
class FeatureLifecycleScopeProvider : ScopeProvider {

    private val lifecycleSubject = BehaviorSubject.create<Unit>()

    fun onDestroy() = lifecycleSubject.onNext(Unit)

    override fun requestScope(): CompletableSource = lifecycleSubject.hide()
            .ignoreElements()

}