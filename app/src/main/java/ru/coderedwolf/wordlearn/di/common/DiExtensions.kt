package ru.coderedwolf.wordlearn.di.common

import androidx.fragment.app.Fragment

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
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