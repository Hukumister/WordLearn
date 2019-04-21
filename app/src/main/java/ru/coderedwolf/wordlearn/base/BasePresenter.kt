package ru.coderedwolf.wordlearn.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Base presenter any presenter of the application must extend. It provides initial injections and
 * required methods.
 *
 * @param V the type of the View the presenter is based on
 * @constructor Injects the required dependencies
 */
abstract class BasePresenter<V : MvpView>(
    private val foregroundContext: CoroutineContext = Dispatchers.Main
) : MvpPresenter<V>(), CoroutineScope {

    private val mParentJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = foregroundContext + mParentJob

    final override fun onDestroy() {
        super.onDestroy()
        onViewDestroyed()
        mParentJob.cancelChildren()
    }

    fun launchUI(block: suspend CoroutineScope.() -> Unit) {
        launch { block.invoke(this) }
    }

    /**
     * This method may be called when the presenter view is destroyed
     */
    open fun onViewDestroyed() {}

    /**
     * Optimal method should be call, onBackPressed on Activity or Fragment
     */
    open fun onBackPressed() {}
}