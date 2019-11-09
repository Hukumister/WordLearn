package ru.coderedwolf.wordlearn.common.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.MapKey
import ru.coderedwolf.wordlearn.common.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.terrakok.cicerone.Router
import kotlin.reflect.KClass

interface ComponentDependencies

typealias ComponentDependenciesProvider = Map<Class<out ComponentDependencies>, @JvmSuppressWildcards ComponentDependencies>

interface HasChildDependencies {
    val dependencies: ComponentDependenciesProvider
}

@Target(AnnotationTarget.FUNCTION)
@MapKey
annotation class ComponentDependenciesKey(val value: KClass<out ComponentDependencies>)

inline fun <reified T : ComponentDependencies> Context.findComponentDependencies(): T {
    val depsProvider: HasChildDependencies =
        if (applicationContext is HasChildDependencies) {
            applicationContext as HasChildDependencies
        } else {
            throw IllegalStateException("Can't find suitable ${HasChildDependencies::class.java.simpleName} for $this")
        }
    return depsProvider.dependencies[T::class.java] as T
}

inline fun <reified T : ComponentDependencies> Fragment.findComponentDependencies(): T {
    var depsProviderFragment = parentFragment
    while (depsProviderFragment !is HasChildDependencies?) {
        depsProviderFragment = depsProviderFragment?.parentFragment
    }
    val depsProvider: HasChildDependencies = depsProviderFragment ?: when {
        activity is HasChildDependencies -> activity as HasChildDependencies
        activity?.application is HasChildDependencies -> activity?.application as HasChildDependencies
        else -> throw IllegalStateException("Can't find suitable ${HasChildDependencies::class.java.simpleName} for $this")
    }
    return depsProvider.dependencies[T::class.java] as T
}

interface CommonDependencies : ComponentDependencies {
    fun dispatchersProvider(): DispatchersProvider
    fun schedulerProvider(): SchedulerProvider
}

interface FlowDependencies : CommonDependencies {
    fun router(): Router
}

interface ScreenDependencies : CommonDependencies {
    fun flowRouter(): FlowRouter
}

interface ViewModelDependencies {
    fun viewModelFactory(): ViewModelProvider.Factory
}