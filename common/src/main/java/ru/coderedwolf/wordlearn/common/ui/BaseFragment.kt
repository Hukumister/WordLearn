package ru.coderedwolf.wordlearn.common.ui

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.coderedwolf.wordlearn.common.di.ComponentManager.clearInjector
import ru.coderedwolf.wordlearn.common.di.ComponentManager.inject
import ru.coderedwolf.wordlearn.common.di.generateComponentName
import ru.coderedwolf.wordlearn.common.util.ContextExtensionsHolder

const val STATE_COMPONENT_NAME = "state_component_name"

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes), ContextExtensionsHolder {

    private var fragmentComponentName: String = ""
    private var instanceStateSaved: Boolean = false

    override val extensionContext: Context
        get() = requireContext()

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponentName = savedInstanceState?.getString(STATE_COMPONENT_NAME) ?: generateComponentName()
        inject(fragmentComponentName)
        super.onCreate(savedInstanceState)
    }

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

    private fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved) || (parentFragment as? BaseFragment)?.isRealRemoving() ?: false

    @CallSuper
    open fun onRealRemoving() {
        featureLifecycleScopeProvider.onDestroy()
        featureDisposeCompositeDisposable.dispose()
        clearInjector(fragmentComponentName)
    }

    open fun onBackPressed() = Unit

    @CheckReturnValue
    fun <T> Observable<T>.autoDisposable(): ObservableSubscribeProxy<T> = autoDispose(featureLifecycleScopeProvider)

    fun Disposable.autoDispose() = featureDisposeCompositeDisposable.add(this)
}