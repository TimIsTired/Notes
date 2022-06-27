package com.timistired.notes.util.log

interface ILogger {

    /**
     * Logs a given text.
     *
     * @param tag a text to add to the exception-log, for more context
     * @param debugMessage the text to log
     * */
    fun logMessage(tag: String, debugMessage: String)

    /**
     * Logs a given [Exception].
     *
     * @param tag a text to add to the exception-log, for more context
     * @param error the exception to log
     * */
    fun logError(tag: String, error: Exception)
}
