package com.example.localdata.domain.usecase

import com.example.localdata.domain.repository.DaysRepository
import com.example.localdata.domain.repository.SettingsRepository
import com.example.localdata.model.Day
import com.example.localdata.model.Settings
import com.example.localdata.model.of
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetDayUseCaseImplTest {

    private val localDate: LocalDate = mockk()
    private val settings: Settings = mockk()
    private val day: Day = mockk()

    private val dayRepository: DaysRepository = mockk()
    private val settingsRepository: SettingsRepository = mockk()

    private lateinit var useCase: GetDayUseCaseImpl

    @Before
    fun setUp() {
        useCase = GetDayUseCaseImpl(dayRepository, settingsRepository)
    }

    @Test
    fun invokeDayExist() = runTest {
        // Arrange
        coEvery { settingsRepository.getSettingsFLow() } returns flowOf(settings)
        coEvery { dayRepository.getDay(localDate) } returns flowOf(day)

        val expected = flowOf(day)

        // Act
        val actual = useCase(localDate)

        // Assert
        assertThat(actual.first()).isEqualTo(expected.first())
        coVerifySequence {
            settingsRepository.getSettingsFLow()
            dayRepository.getDay(localDate)
        }
    }

    @Test
    fun invokeNoDay() = runTest {
        // Arrange
        val settings = Settings()
        coEvery { settingsRepository.getSettingsFLow() } returns flowOf(settings)
        coEvery { dayRepository.getDay(localDate) } returns flowOf(null)

        val expected = flowOf(Day.of(localDate, settings))

        // Act
        val actual = useCase(localDate)

        // Assert
        assertThat(actual.first()).isEqualTo(expected.first())
        coVerifySequence {
            settingsRepository.getSettingsFLow()
            dayRepository.getDay(localDate)
        }
    }
}