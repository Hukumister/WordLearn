package ru.coderedwolf.wordlearn.common.ui

import com.badoo.mvicore.feature.Feature
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.CompositeDisposable


/**
 * @author CodeRedWolf. Date 19.09.2019.
 */
abstract class FeatureDisposeFragment : BaseFragment() {

    private val featureLifecycleScopeProvider = FeatureLifecycleScopeProvider()

    private val featureDisposeCompositeDisposable = CompositeDisposable()

    @CheckReturnValue
    fun <T> Observable<T>.autoDisposable(): ObservableSubscribeProxy<T> = autoDispose(featureLifecycleScopeProvider)

    fun <Wish : Any, State : Any, News : Any> Feature<Wish, State, News>.autoDispose() = featureDisposeCompositeDisposable.add(this)

    override fun onRealRemoving() {
        super.onRealRemoving()
        featureLifecycleScopeProvider.onDestroy()
        featureDisposeCompositeDisposable.clear()
    }
}

