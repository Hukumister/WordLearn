package ru.coderedwolf.wordlearn.domain.system

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.coderedwolf.wordlearn.common.domain.system.DispatchersProvider

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
@ExperimentalCoroutinesApi
class TestDispatchersProvider : DispatchersProvider {

    override fun ui() = Dispatchers.Unconfined
    override fun io() = Dispatchers.Unconfined
    override fun computation() = Dispatchers.Unconfined
}