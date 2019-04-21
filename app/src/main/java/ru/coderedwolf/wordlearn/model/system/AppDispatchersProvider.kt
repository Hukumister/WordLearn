package ru.coderedwolf.wordlearn.model.system

import kotlinx.coroutines.Dispatchers

class AppDispatchersProvider : DispatchersProvider {

    override fun ui() = Dispatchers.Main
    override fun io() = Dispatchers.IO
    override fun computation() = Dispatchers.Default
}