package ru.haroncode.wordlearn.common.domain.system

interface Logger {

    fun debug(tag: String, message: String? = null, throwable: Throwable? = null, vararg args: Any?)

    fun info(tag: String, message: String? = null, throwable: Throwable? = null, vararg args: Any?)

    fun warning(tag: String, message: String? = null, throwable: Throwable? = null, vararg args: Any?)

    fun error(tag: String, message: String? = null, throwable: Throwable? = null, vararg args: Any?)
}
