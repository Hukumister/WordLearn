package ru.coderedwolf.wordlearn.domain.system

import kotlinx.coroutines.Dispatchers
import ru.coderedwolf.wordlearn.common.DispatchersProvider
import javax.inject.Inject

class AppDispatchersProvider @Inject constructor() : DispatchersProvider {
    override fun ui() = Dispatchers.Main
    override fun io() = Dispatchers.IO
    override fun computation() = Dispatchers.Default
}