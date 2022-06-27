package com.timistired.notes.util.log

import android.util.Log

/**
 * Simplified implementation of [ILogger].
 * */
class SimpleLogger : ILogger {

    override fun logMessage(tag: String, debugMessage: String) {
        Log.d(tag, debugMessage)
    }

    override fun logError(tag: String, error: Exception) {
        /*
        Simply print the stacktrace. In a real world/production scenario we would forward the error
        to some kind of crash reporter (e.g. use Firebase Crashlytics)
        */
        Log.e(tag, error.message, error)
    }
}