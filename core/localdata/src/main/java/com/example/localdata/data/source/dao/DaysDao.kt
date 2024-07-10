package com.example.localdata.data.source.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.localdata.model.Day
import com.example.localdata.model.DaySettings
import com.example.localdata.model.DbConstants.COLUMN_ID
import com.example.localdata.model.DbConstants.DAYS_TABLE_NAME
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DaysDao {

    @Query("SELECT COUNT(*) FROM $DAYS_TABLE_NAME WHERE steps >= goal")
    fun getTreeCount(): Flow<Int>

    @Query("SELECT * FROM $DAYS_TABLE_NAME ORDER BY $COLUMN_ID ASC LIMIT 1")
    fun getFirstDay(): Flow<Day?>

    @Query("SELECT * FROM $DAYS_TABLE_NAME WHERE $COLUMN_ID = :date")
    fun getDay(date: LocalDate): Flow<Day?>

    @Query("SELECT * FROM $DAYS_TABLE_NAME")
    suspend fun getAllDays(): List<Day>

    @Query("SELECT * FROM $DAYS_TABLE_NAME WHERE $COLUMN_ID BETWEEN :start AND :endInclusive")
    fun getDays(start: LocalDate, endInclusive: LocalDate): Flow<List<Day>>

    @Upsert
    suspend fun upsertDay(day: Day)

    @Update(entity = Day::class)
    suspend fun updateDaySettings(day: DaySettings)
}