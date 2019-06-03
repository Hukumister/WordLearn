package ru.coderedwolf.wordlearn.di.module

import ru.coderedwolf.wordlearn.domain.reporitory.WordsCategoryRepository
import ru.coderedwolf.wordlearn.domain.reporitory.WordsCategoryRepositoryImpl
import ru.coderedwolf.wordlearn.domain.interactors.WordsCategoryInteractor
import ru.coderedwolf.wordlearn.domain.interactors.WordsCategoryInteractorImpl
import ru.coderedwolf.wordlearn.domain.mappers.CategoryMapper
import toothpick.config.Module

/**
 * @author CodeRedWolf. Date 08.05.2019.
 */
class DataModule : Module() {
    init {

        bind(WordsCategoryRepository::class.java).to(WordsCategoryRepositoryImpl::class.java)
        bind(WordsCategoryInteractor::class.java).to(WordsCategoryInteractorImpl::class.java)
        bind(CategoryMapper::class.java).singletonInScope()
    }
}