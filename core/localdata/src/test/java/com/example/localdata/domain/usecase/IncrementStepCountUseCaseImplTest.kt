package com.example.localdata.domain.usecase

import com.example.localdata.domain.repository.DaysRepository
import com.example.localdata.model.Day
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class IncrementStepCountUseCaseImplTest {

    private val localDate: LocalDate = mockk()
    private val dayRepository: DaysRepository = mockk()
    private val getDayUseCase: GetDayUseCase = mockk()
    private lateinit var useCaseImpl: IncrementStepCountUseCaseImpl

    @Before
    fun setUp() {
        useCaseImpl = IncrementStepCountUseCaseImpl(dayRepository, getDayUseCase)
    }

    @Test
    fun invoke() = runTest {
        // Arrange
        val day = Day(localDate, steps = DEFAULT_STEPS_COUNT)
        val updatedDay = Day(localDate, steps = UPDATED_STEPS_COUNT)
        coEvery { getDayUseCase(localDate) } returns flowOf(day)

        coJustRun { dayRepository.upsertDay(updatedDay) }

        // Act
        useCaseImpl(localDate, NEW_STEPS)

        // Assert
        coVerifySequence {
            getDayUseCase(localDate)
            dayRepository.upsertDay(updatedDay)
        }
    }

    private companion object {
        const val NEW_STEPS = 10
        const val DEFAULT_STEPS_COUNT = 7500
        const val UPDATED_STEPS_COUNT = 7510
    }
}