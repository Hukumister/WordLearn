package ru.coderedwolf.wordlearn.di.common

import dagger.MapKey
import kotlin.reflect.KClass

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ComponentDependenciesKey(val value: KClass<out ComponentDependencies>)