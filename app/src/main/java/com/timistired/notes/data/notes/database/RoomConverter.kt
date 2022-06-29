package com.timistired.notes.data.notes.database

import androidx.room.TypeConverter
import java.util.*

class RoomConverter {

    @TypeConverter
    fun fromDate(value: Date): Long {
        return value.time
    }

    @TypeConverter
    fun toDate(value: Long): Date {
        return Date(value)
    }
}