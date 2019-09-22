package ru.coderedwolf.wordlearn.wordflow.presentation

import com.badoo.mvicore.binder.using
import com.badoo.mvicore.feature.Feature
import ru.coderedwolf.wordlearn.common.presentation.ErrorHandler
import ru.coderedwolf.wordlearn.common.presentation.FeatureAndroidBindings
import ru.coderedwolf.wordlearn.wordflow.ui.CreateWordFragment
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 24.08.2019.
 */
class CreateWordFragmentBindings @Inject constructor(
        view: CreateWordFragment,
        private val createWordFeature: CreateWordFeature
) : FeatureAndroidBindings<CreateWordFragment>(view) {

    override fun initFeatures(): (CreateWordFragment) -> List<Feature<*, *, *>> = { view ->
        binder.bind(createWordFeature to view using ViewModelTransformer())
        binder.bind(view to createWordFeature using UiEventTransformer())

        listOf(createWordFeature)
    }

}