package com.example.localdata.data.source.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.localdata.data.source.ForestDatabase
import com.example.localdata.model.Day
import com.example.localdata.model.DaySettings
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class DaysDaoTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val localDate = LocalDate.now()
    private lateinit var database: ForestDatabase
    private lateinit var daysDao: DaysDao

    @Before
    fun setUp() {

        database = Room.inMemoryDatabaseBuilder(context, ForestDatabase::class.java).build().also {
            daysDao = it.daysDao
        }
    }

    @After
    fun tearDown() {
        database.close()
        database.clearAllTables()
    }

    @Test
    fun upsertAndGetDay() = runTest {
        // Arrange
        val day = Day(localDate)
        daysDao.upsertDay(day)

        // Act
        val actual = daysDao.getDay(localDate).first()

        // Assert
        assertThat(actual).isEqualTo(day)
    }

    @Test
    fun upsertThreeDaysAndGetFirstDayAndGetAllDaysAndGetTreeCount() = runTest {
        // Arrange
        val secondDay = Day(localDate.minusDays(1), SECOND_DAY_STEPS, SECOND_DAY_GOAL)
        daysDao.upsertDay(secondDay)

        val thirdDay = Day(localDate, THIRD_DAY_STEPS, THIRD_DAY_GOALS)
        daysDao.upsertDay(thirdDay)

        val firstDay = Day(localDate.minusDays(2), FIRST_DAY_STEPS, FIRST_DAY_GOAL)
        daysDao.upsertDay(firstDay)

        // Act
        val actualAllDays = daysDao.getAllDays()
        val actualFirstDay = daysDao.getFirstDay().first()
        val actualTreeCount = daysDao.getTreeCount().first()

        // Assert
        assertThat(actualAllDays).isEqualTo(listOf(firstDay, secondDay, thirdDay))
        assertThat(actualFirstDay).isEqualTo(firstDay)
        assertThat(actualTreeCount).isEqualTo(2)
    }

    @Test
    fun updateDaySettings() = runTest {
        // Arrange
        val day = Day(localDate)
        daysDao.upsertDay(day)

        daysDao.updateDaySettings(DaySettings(localDate, goal = FIRST_DAY_GOAL))

        // Act
        val actual = daysDao.getDay(localDate).first()

        // Assert
        assertThat(actual).isEqualTo(day.copy(goal = FIRST_DAY_GOAL))
    }

    private companion object {

        const val FIRST_DAY_STEPS = 8000
        const val FIRST_DAY_GOAL = 7000

        const val SECOND_DAY_STEPS = 5500
        const val SECOND_DAY_GOAL = 6000

        const val THIRD_DAY_STEPS = 4000
        const val THIRD_DAY_GOALS = 3999
    }
}