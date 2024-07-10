package com.example.localdata.data.repository

import com.example.localdata.data.source.dao.DaysDao
import com.example.localdata.domain.repository.DaysRepository
import com.example.localdata.model.Day
import com.example.localdata.model.DaySettings
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DaysRepositoryImpl(
    private val dao: DaysDao
) : DaysRepository {

    override fun getTreeCount(): Flow<Int> {
        return dao.getTreeCount()
    }

    override fun getFirstDay(): Flow<Day?> {
        return dao.getFirstDay()
    }

    override fun getDay(date: LocalDate): Flow<Day?> {
        return dao.getDay(date)
    }

    override suspend fun getAllDays(): List<Day> {
        return dao.getAllDays()
    }

    override fun getDays(range: ClosedRange<LocalDate>): Flow<List<Day>> {
        return dao.getDays(range.start, range.endInclusive)
    }

    override suspend fun upsertDay(day: Day) {
        dao.upsertDay(day)
    }

    override suspend fun updateDaySettings(daySettings: DaySettings) {
        dao.updateDaySettings(daySettings)
    }
}