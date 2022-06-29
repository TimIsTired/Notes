package com.timistired.notes.util.dateHelper

import java.text.SimpleDateFormat
import java.util.*

class DateHelper : IDateHelper {

    /**
     * Converts a given [Date] to a formatted string.
     *
     * @param date the date to convert
     * @return a string representation of the date
     * */
    override fun getDateAsString(date: Date): String {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    companion object {
        private const val pattern = "dd.MM.yyyy HH:mm"
    }
}