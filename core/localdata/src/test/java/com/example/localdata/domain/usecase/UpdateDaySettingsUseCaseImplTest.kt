package com.example.localdata.domain.usecase

import com.example.localdata.domain.repository.DaysRepository
import com.example.localdata.model.DaySettings
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UpdateDaySettingsUseCaseImplTest {

    private val daySettings: DaySettings = mockk()

    private val dayRepository: DaysRepository = mockk()
    private val useCase = UpdateDaySettingsUseCaseImpl(dayRepository)

    @Test
    fun invoke() = runTest {
        // Arrange
        coJustRun { dayRepository.updateDaySettings(daySettings) }

        // Act
        useCase(daySettings)

        // Assert
        coVerifySequence {
            dayRepository.updateDaySettings(daySettings)
        }
    }
}