package ru.coderedwolf.wordlearn.ui.base

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import ru.coderedwolf.wordlearn.di.DI
import ru.coderedwolf.wordlearn.di.ScopeManager
import ru.coderedwolf.wordlearn.extension.scopeName
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick

private const val STATE_SCOPE_NAME = "state_scope_name"
private const val STATE_SCOPE_WAS_CLOSED = "state_scope_was_closed"

abstract class BaseFragment : MvpAppCompatFragment() {

    abstract val layoutRes: Int

    private var instanceStateSaved: Boolean = false

    private val viewHandler = Handler()

    protected open val parentScopeName: String by lazy {
        (parentFragment as? BaseFragment)?.fragmentScopeName ?: DI.APP_SCOPE
    }

    protected open val scopeModuleInstaller: (Scope) -> Unit = {}

    private lateinit var fragmentScopeName: String
    protected lateinit var scope: Scope
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        val scopeWasClosed = savedInstanceState?.getBoolean(STATE_SCOPE_WAS_CLOSED) ?: true
        fragmentScopeName = savedInstanceState?.getString(STATE_SCOPE_NAME) ?: scopeName()

        val scopeNameNotExist = ScopeManager.exist(fragmentScopeName).not()
        val scopeIsNotInit = scopeNameNotExist || scopeWasClosed
        scope = Toothpick.openScopes(parentScopeName, fragmentScopeName)
                .apply {
                    if (scopeIsNotInit) {
                        Timber.d("Init new UI scope: $fragmentScopeName")
                        scopeModuleInstaller(this)
                        ScopeManager.addName(fragmentScopeName)
                    } else {
                        Timber.d("Get exist UI scope: $fragmentScopeName")
                    }
                }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(layoutRes, container, false)!!

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    //fix for async views (like swipeToRefresh and RecyclerView)
    //if synchronously call actions on swipeToRefresh in sequence show and hide then swipeToRefresh will not hidden
    protected fun postViewAction(action: () -> Unit) {
        viewHandler.post(action)
    }

    protected fun postViewAction(delay: Long, action: () -> Unit) {
        viewHandler.postDelayed(action, delay)
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
            Toothpick.closeScope(scope.name)
            ScopeManager.removeName(fragmentScopeName)
        }
    }

    private fun isRealRemoving(): Boolean =
            (isRemoving && !instanceStateSaved) //because isRemoving == true for fragment in backstack on screen rotation
                    || ((parentFragment as? BaseFragment)?.isRealRemoving() ?: false)

    private fun needCloseScope(): Boolean =
            when {
                activity?.isChangingConfigurations == true -> false
                activity?.isFinishing == true -> true
                else -> isRealRemoving()
            }

    open fun onBackPressed() {}
}