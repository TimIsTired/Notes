package com.timistired.notes.exception

class LocationUnavailableException(private val sourceException: Exception) : Exception() {

    override val message: String
        get() = run {
            val builder = StringBuilder("Could not fetch location!")
            sourceException.message?.let { builder.appendLine(it) }
            builder.toString()
        }
}