package ru.coderedwolf.wordlearn.common.di

import android.content.Context
import androidx.fragment.app.Fragment
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

inline fun <reified T : ComponentDependencies> Context.findComponentDependencies(): T =
    findComponentDependencies(T::class.java)

fun <T : ComponentDependencies> Context.findComponentDependencies(clazz: Class<T>): T =
    applicationContext?.findDeps(clazz)
        ?: throw IllegalStateException("Can't find suitable ${HasChildDependencies::class.java.simpleName} for $this")

inline fun <reified T : ComponentDependencies> Fragment.findComponentDependencies(): T =
    findComponentDependencies(T::class.java)

fun <T : ComponentDependencies> Fragment.findComponentDependencies(clazz: Class<T>): T =
    findDepsFromParents(clazz)
        ?: activity?.findDeps(clazz)
        ?: activity?.application?.findDeps(clazz)
        ?: throw IllegalStateException("Can't find suitable ${HasChildDependencies::class.java.simpleName} for $this")

private fun <T : ComponentDependencies> Fragment.findDepsFromParents(clazz: Class<T>): T? {
    var depsProviderFragment = parentFragment
    while (depsProviderFragment != null) {
        val deps = depsProviderFragment.findDeps(clazz)
        if (deps != null) {
            return deps
        }
        depsProviderFragment = depsProviderFragment.parentFragment
    }
    return null
}

@Suppress("UNCHECKED_CAST")
private fun <T : ComponentDependencies> Any.findDeps(clazz: Class<T>): T? {
    if (this !is HasChildDependencies) return null
    return dependencies[clazz]
        ?.takeIf(clazz::isInstance)
        ?.let(clazz::cast)
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