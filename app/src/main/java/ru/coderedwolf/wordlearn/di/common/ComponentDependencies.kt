package ru.coderedwolf.wordlearn.di.common

interface ComponentDependencies

typealias ComponentDependenciesProvider = Map<Class<out ComponentDependencies>, @JvmSuppressWildcards ComponentDependencies>

interface HasChildDependencies {
    val dependencies: ComponentDependenciesProvider
}