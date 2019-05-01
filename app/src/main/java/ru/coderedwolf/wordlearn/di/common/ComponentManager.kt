package ru.coderedwolf.wordlearn.di.common

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
object ComponentManager {
    private val components = mutableMapOf<String, DaggerComponent>()

    fun getOrPutComponent(componentName: String, componentBuilder: () -> DaggerComponent) =
        components.getOrPut(componentName, componentBuilder)

    fun clearComponent(componentName: String) {
        components.remove(componentName)
    }
}

