package ru.coderedwolf.wordlearn.common.presentation

import androidx.lifecycle.LifecycleOwner
import com.badoo.mvicore.android.AndroidBindings
import io.reactivex.disposables.Disposable
import ru.coderedwolf.wordlearn.common.ui.FeatureDisposeFragment

/**
 * @author CodeRedWolf. Date 22.09.2019.
 */
abstract class FeatureAndroidBindings<T : FeatureDisposeFragment>(
        lifecycleOwner: LifecycleOwner
) : AndroidBindings<T>(lifecycleOwner) {

    final override fun setup(view: T) {
        val features = initFeatures().invoke(view)
        view.apply { autoDisposeFeatures(features) }
    }

    abstract fun initFeatures(): (T) -> List<Disposable>

    private fun T.autoDisposeFeatures(features: List<Disposable>) = features
            .forEach { feature -> feature.autoDispose() }

}