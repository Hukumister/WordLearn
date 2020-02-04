package ru.haroncode.wordlearn.common.di

inline fun <T> InjectorBuilder(
    crossinline builder: T.() -> Injector<T>
): InjectorBuilder<*> = object : InjectorBuilder<T> {
    override fun build(target: T): Injector<T> = builder(target)
}

interface InjectorBuilder<T> {
    fun build(target: T): Injector<T>
}
