package com.example.localdata.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.localdata.data.source.dao.DaysDao
import com.example.localdata.data.source.util.Converters
import com.example.localdata.model.Day

@Database(entities = [Day::class], version = 1)
@TypeConverters(Converters::class)
abstract class ForestDatabase : RoomDatabase() {

    abstract val daysDao: DaysDao

    companion object {
        const val DATABASE_NAME = "forest_database"
    }
}