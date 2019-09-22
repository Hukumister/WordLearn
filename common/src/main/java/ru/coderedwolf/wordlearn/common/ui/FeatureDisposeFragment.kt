package ru.coderedwolf.wordlearn.common.ui

import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * @author CodeRedWolf. Date 19.09.2019.
 */
abstract class FeatureDisposeFragment : BaseFragment() {

    private val featureLifecycleScopeProvider = FeatureLifecycleScopeProvider()

    private val featureDisposeCompositeDisposable = CompositeDisposable()

    @CheckReturnValue
    fun <T> Observable<T>.autoDisposable(): ObservableSubscribeProxy<T> = autoDispose(featureLifecycleScopeProvider)

    fun Disposable.autoDispose() = featureDisposeCompositeDisposable.add(this)

    override fun onRealRemoving() {
        super.onRealRemoving()
        featureLifecycleScopeProvider.onDestroy()
        featureDisposeCompositeDisposable.clear()
    }
}

