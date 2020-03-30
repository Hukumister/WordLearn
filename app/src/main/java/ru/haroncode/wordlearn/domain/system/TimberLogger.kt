package ru.haroncode.wordlearn.domain.system

import javax.inject.Inject
import ru.haroncode.wordlearn.common.domain.system.Logger
import timber.log.Timber

class TimberLogger @Inject constructor() : Logger {

    override fun debug(tag: String, message: String?, throwable: Throwable?, vararg args: Any?) {
        Timber.tag(tag).d(throwable, message, *args)
    }

    override fun info(tag: String, message: String?, throwable: Throwable?, vararg args: Any?) {
        Timber.tag(tag).i(throwable, message, *args)
    }

    override fun warning(tag: String, message: String?, throwable: Throwable?, vararg args: Any?) {
        Timber.tag(tag).w(throwable, message, *args)
    }

    override fun error(tag: String, message: String?, throwable: Throwable?, vararg args: Any?) {
        Timber.tag(tag).e(throwable, message, *args)
    }
}
