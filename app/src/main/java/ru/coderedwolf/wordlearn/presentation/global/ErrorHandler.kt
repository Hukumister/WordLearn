package ru.coderedwolf.wordlearn.presentation.global

import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.domain.system.ResourceProvider
import timber.log.Timber
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 09.06.2019.
 */
class ErrorHandler @Inject constructor(
        private val resourceManager: ResourceProvider
) {

    fun proceed(error: Throwable): String {
        Timber.e(error)
        return error.message ?: resourceManager.getString(R.string.unknown_error)
    }
}