package ru.haroncode.wordlearn.common.rx

import io.reactivex.exceptions.Exceptions
import ru.haroncode.wordlearn.common.domain.system.Logger

object RxRecorder {

    private const val TAG = "RxRecorder"

    /**
     * Index of stack frame where RxThrowable.javaPrintStackTrace... method has been called
     */
    private const val CALLER_INDEX = 1

    fun printStackTrace(logger: Logger): (Throwable) -> Unit {
        val stackTraceThrowable = Throwable()
        return printStackTrace(logger, stackTraceThrowable, false)
    }

    fun printStackTraceAndPropagate(logger: Logger): (Throwable) -> Unit {
        val stackTraceThrowable = Throwable()
        return printStackTrace(logger, stackTraceThrowable, true)
    }

    private fun printStackTrace(
        logger: Logger,
        stackTraceThrowable: Throwable,
        propagate: Boolean
    ): (Throwable) -> Unit = { throwable ->
        val traces = stackTraceThrowable.stackTrace
        if (traces.size > CALLER_INDEX + 1) {
            val trace = traces[CALLER_INDEX]

            val fileName = trace.fileName
            val tag = (if (fileName.isNullOrEmpty()) trace.className else fileName)
                .replace(".java", "")
                .replace(".kt", "")

            val lineNumber = trace.lineNumber
            val formattedLine = if (lineNumber > 0) String.format(" at line %s", lineNumber) else ""
            val message = String.format(
                "Exception in %1\$s.%2\$s()%3\$s",
                trace.className, trace.methodName, formattedLine
            )

            logger.error(tag, message, throwable)
        } else {
            logger.error(TAG, throwable.message ?: "", throwable)
        }

        if (propagate) {
            throw Exceptions.propagate(throwable)
        }
    }
}
