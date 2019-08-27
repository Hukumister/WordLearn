package ru.coderedwolf.wordlearn.mainflow.di

import dagger.Component
import ru.coderedwolf.wordlearn.common.di.Injector
import ru.coderedwolf.wordlearn.common.di.PerFragment
import ru.coderedwolf.wordlearn.common.di.ScreenDependencies
import ru.coderedwolf.wordlearn.mainflow.presentation.WordsCategoryReachableFlows
import ru.coderedwolf.wordlearn.mainflow.ui.WordsCategoryFragment
import ru.coderedwolf.wordlearn.wordscategory.domain.WordsCategoryInteractor

@PerFragment
@Component(

    dependencies = [
        WordsCategoryDependencies::class
    ]
)
interface WordsCategoryComponent : Injector<WordsCategoryFragment>

interface WordsCategoryDependencies : ScreenDependencies {
    fun wordsCategoryInteractor(): WordsCategoryInteractor
    fun wordsCategoryReachableFlows(): WordsCategoryReachableFlows
}