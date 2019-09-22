package ru.coderedwolf.wordlearn.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpAppCompatFragment
import ru.coderedwolf.wordlearn.common.di.ComponentManager.clearInjector
import ru.coderedwolf.wordlearn.common.di.ComponentManager.inject
import ru.coderedwolf.wordlearn.common.di.generateComponentName

const val STATE_COMPONENT_NAME = "state_component_name"

abstract class BaseFragment : MvpAppCompatFragment() {
    protected abstract val layoutRes: Int
    private lateinit var fragmentComponentName: String
    private var instanceStateSaved: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponentName = savedInstanceState?.getString(STATE_COMPONENT_NAME) ?: generateComponentName()
        inject(fragmentComponentName)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutRes, container, false)

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
        outState.putString(STATE_COMPONENT_NAME, fragmentComponentName)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needToCallOnRealRemoving()) {
            onRealRemoving()
        }
    }

    private fun needToCallOnRealRemoving(): Boolean = when {
        activity?.isChangingConfigurations == true -> false
        activity?.isFinishing == true -> true
        else -> isRealRemoving()
    }

    private val featureLifecycleScopeProvider = FeatureLifecycleScopeProvider()

    private val featureDisposeCompositeDisposable = CompositeDisposable()

    internal fun isRealRemoving(): Boolean = (isRemoving && !instanceStateSaved) || (parentFragment as BaseFragment).isRealRemoving()

    @CallSuper
    open fun onRealRemoving(){
        featureLifecycleScopeProvider.onDestroy()
        featureDisposeCompositeDisposable.clear()
        clearInjector(fragmentComponentName)
    }

    open fun onBackPressed() = Unit

    @CheckReturnValue
    fun <T> Observable<T>.autoDisposable(): ObservableSubscribeProxy<T> = autoDispose(featureLifecycleScopeProvider)

    fun Disposable.autoDispose() = featureDisposeCompositeDisposable.add(this)


}