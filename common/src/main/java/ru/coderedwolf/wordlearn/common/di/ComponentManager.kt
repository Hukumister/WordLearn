package ru.coderedwolf.wordlearn.common.di

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
        val injectorBuilder = builders[javaClass] as? InjectorBuilder<T> ?: return
        getOrPutInjector(injectorName, injectorBuilder).inject(this)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> T.getOrPutInjector(
        injectorName: String,
        injectorBuilder: InjectorBuilder<T>
    ): Injector<T> = injectors.getOrPut(injectorName, { injectorBuilder.build(this) }) as Injector<T>

    @Suppress("UNCHECKED_CAST")
    fun <T> getInjector(injectorName: String): Injector<T> = injectors.getValue(injectorName) as Injector<T>

    fun clearInjector(injectorName: String) {
        injectors.remove(injectorName)
    }
}