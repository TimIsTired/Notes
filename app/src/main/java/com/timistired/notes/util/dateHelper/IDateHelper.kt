package com.timistired.notes.util.dateHelper

import java.util.*

interface IDateHelper {

    /**
     * Converts a given [Date] to a formatted string.
     *
     * @param date the date to convert
     * @return a string representation of the date
     * */
    fun getDateAsString(date: Date): String
}