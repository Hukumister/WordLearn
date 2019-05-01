package ru.coderedwolf.wordlearn.ui.base

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.coderedwolf.wordlearn.di.common.ComponentManager
import ru.coderedwolf.wordlearn.di.common.DaggerComponent
import ru.coderedwolf.wordlearn.di.common.EMPTY_COMPONENT
import ru.coderedwolf.wordlearn.extension.scopeName
import ru.coderedwolf.wordlearn.moxy.androidx.MvpAppCompatFragment
import timber.log.Timber

private const val STATE_SCOPE_NAME = "state_scope_name"
private const val STATE_SCOPE_WAS_CLOSED = "state_scope_was_closed"

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */
abstract class BaseFragment : MvpAppCompatFragment() {

    abstract val layoutRes: Int

    private val viewHandler = Handler()

    private var instanceStateSaved: Boolean = false

    private lateinit var fragmentScopeName: String

    protected open val componentBuilder: () -> DaggerComponent = { EMPTY_COMPONENT }

    protected inline fun <reified T : DaggerComponent> component(): T =
        ComponentManager.getOrPutComponent(this.javaClass.simpleName, componentBuilder) as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentScopeName = savedInstanceState?.getString(STATE_SCOPE_NAME) ?: scopeName()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(layoutRes, container, false)!!

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    protected fun postViewAction(action: () -> Unit) {
        viewHandler.post(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewHandler.removeCallbacksAndMessages(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
        outState.putString(STATE_SCOPE_NAME, fragmentScopeName)
        outState.putBoolean(STATE_SCOPE_WAS_CLOSED, needCloseScope()) //save it but will be used only if destroyed
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needCloseScope()) {
            //destroy this fragment with scope
            Timber.d("Destroy UI scope: $fragmentScopeName")
            ComponentManager.clearComponent(fragmentScopeName)
        }
    }

    private fun needCloseScope(): Boolean =
        when {
            activity?.isChangingConfigurations == true -> false
            activity?.isFinishing == true -> true
            else -> isRealRemoving()
        }

    private fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved) //because isRemoving == true for fragment in backstack on screen rotation
                || ((parentFragment as? BaseFragment)?.isRealRemoving() ?: false)

    open fun onBackPressed() {}
}