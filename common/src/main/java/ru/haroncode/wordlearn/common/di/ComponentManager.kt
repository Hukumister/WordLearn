package ru.haroncode.wordlearn.common.di

typealias BuildersProvider = Map<Class<*>, InjectorBuilder<*>>

typealias InjectorsProvider = HashMap<String, Injector<*>>

fun Any.generateComponentName(): String = "${javaClass.simpleName}_${hashCode()}"

object ComponentManager {
    private lateinit var builders: BuildersProvider
    private val injectors = InjectorsProvider()

    fun initBuilders(builders: BuildersProvider) {
        if (!this::builders.isInitialized) {
            this.builders = builders
        } else {
            throw IllegalStateException("builders have already been initialized")
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> T.inject() {
        val injectorBuilder = builders[javaClass] as? InjectorBuilder<T> ?: return
        injectorBuilder.build(this).inject(this)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> T.inject(injectorName: String) {
        var injector = injectors[injectorName]
        if (injector == null) {
            val injectorBuilder = builders[javaClass] as? InjectorBuilder<T> ?: return
            injector = injectorBuilder.build(this)
            injectors[injectorName] = injector
        }
        (injector as Injector<T>).inject(this)
    }

    fun clearInjector(injectorName: String) {
        injectors.remove(injectorName)
    }
}
