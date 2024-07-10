package com.example.localdata.domain.repository

import kotlinx.coroutines.flow.Flow
import com.example.localdata.model.Day
import com.example.localdata.model.DaySettings
import java.time.LocalDate

interface DaysRepository {

    fun getTreeCount(): Flow<Int>

    fun getFirstDay(): Flow<Day?>

    fun getDay(date: LocalDate): Flow<Day?>

    suspend fun getAllDays(): List<Day>

    fun getDays(range: ClosedRange<LocalDate>): Flow<List<Day>>

    suspend fun upsertDay(day: Day)

    suspend fun updateDaySettings(daySettings: DaySettings)
}