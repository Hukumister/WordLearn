package ru.coderedwolf.wordlearn.common.presentation

import androidx.lifecycle.LifecycleOwner
import com.badoo.mvicore.android.lifecycle.ResumePauseBinderLifecycle
import com.badoo.mvicore.binder.Binder
import io.reactivex.disposables.Disposable
import ru.coderedwolf.wordlearn.common.ui.BaseFragment

/**
 * @author CodeRedWolf. Date 22.09.2019.
 */
abstract class FeatureAndroidBindings<T : BaseFragment>(
        lifecycleOwner: LifecycleOwner
) {

    protected val binder = Binder(
            lifecycle = ResumePauseBinderLifecycle(
                    androidLifecycle = lifecycleOwner.lifecycle
            )
    )

    fun setup(view: T) {
        val features = initFeatures().invoke(view)
        view.apply { autoDisposeFeatures(features) }
    }

    abstract fun initFeatures(): (T) -> List<Disposable>

    private fun T.autoDisposeFeatures(features: List<Disposable>) = features
            .forEach { feature -> feature.autoDispose() }

}