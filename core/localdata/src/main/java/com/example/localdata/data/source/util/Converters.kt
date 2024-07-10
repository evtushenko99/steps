package com.example.localdata.data.source.util

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun localDateToTimestamp(date: LocalDate): Long {
        return date.toEpochDay()
    }

    @TypeConverter
    fun timestampToLocalDate(timestamp: Long): LocalDate {
        return LocalDate.ofEpochDay(timestamp)
    }
}