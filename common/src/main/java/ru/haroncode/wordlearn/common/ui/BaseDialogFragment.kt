package ru.haroncode.wordlearn.common.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.autoDispose
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import ru.haroncode.wordlearn.common.di.ComponentManager
import ru.haroncode.wordlearn.common.di.ComponentManager.inject
import ru.haroncode.wordlearn.common.di.generateComponentName
import ru.haroncode.wordlearn.common.util.ContextExtensionsHolder

/**
 * @author HaronCode.
 */
abstract class BaseDialogFragment : DialogFragment(), ContextExtensionsHolder {

    companion object {

        private const val STATE_COMPONENT_NAME = "state_component_name"
    }

    private var fragmentComponentName: String = ""
    private var instanceStateSaved: Boolean = false

    private val fragmentScopeProvider = FragmentLifecycleScopeProvider()

    override val extensionContext: Context
        get() = requireContext()

    protected abstract val layoutRes: Int

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

    internal fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved) || (parentFragment as? BaseDialogFragment)?.isRealRemoving() ?: false

    override fun onStart() {
        super.onStart()
        fragmentScopeProvider.onStart()
    }

    override fun onStop() {
        super.onStop()
        fragmentScopeProvider.onStop()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentScopeProvider.onAttach()
    }

    override fun onDetach() {
        super.onDetach()
        fragmentScopeProvider.onDetach()
    }

    @CallSuper
    open fun onRealRemoving() {
        ComponentManager.clearInjector(fragmentComponentName)
    }

    @CheckReturnValue
    fun <T> Observable<T>.autoDisposable(): ObservableSubscribeProxy<T> = autoDispose(fragmentScopeProvider)

    @CheckReturnValue
    fun <T> Flowable<T>.autoDisposable(): FlowableSubscribeProxy<T> = autoDispose(fragmentScopeProvider)
}
