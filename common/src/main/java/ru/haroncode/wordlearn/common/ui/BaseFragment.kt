package ru.haroncode.wordlearn.common.ui

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.autoDispose
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import ru.haroncode.wordlearn.common.di.ComponentManager.clearInjector
import ru.haroncode.wordlearn.common.di.ComponentManager.inject
import ru.haroncode.wordlearn.common.di.generateComponentName
import ru.haroncode.wordlearn.common.util.ContextExtensionsHolder

abstract class BaseFragment @JvmOverloads constructor(
    @LayoutRes layoutRes: Int = 0
) : Fragment(layoutRes), ContextExtensionsHolder {

    companion object {

        private const val STATE_COMPONENT_NAME = "state_component_name"
    }

    private var fragmentComponentName: String = ""
    private var instanceStateSaved: Boolean = false

    private val fragmentScopeProvider = FragmentLifecycleScopeProvider()

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

    open fun onBackPressed() = Unit

    @CallSuper
    open fun onRealRemoving() {
        fragmentScopeProvider.onDestroy()
        clearInjector(fragmentComponentName)
    }

    private fun needToCallOnRealRemoving(): Boolean = when {
        activity?.isChangingConfigurations == true -> false
        activity?.isFinishing == true -> true
        else -> isRealRemoving()
    }

    private fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved) || (parentFragment as? BaseFragment)?.isRealRemoving() ?: false

    @CheckReturnValue
    fun <T> Observable<T>.autoDisposable(): ObservableSubscribeProxy<T> = autoDispose(fragmentScopeProvider)

    @CheckReturnValue
    fun <T> Flowable<T>.autoDisposable(): FlowableSubscribeProxy<T> = autoDispose(fragmentScopeProvider)
}
