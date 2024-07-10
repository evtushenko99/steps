package com.example.localdata.domain.usecase

import com.example.localdata.domain.repository.DaysRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class IncrementStepCountUseCaseImpl @Inject constructor(
    private val repository: DaysRepository,
    private val getDayUseCase: GetDayUseCase
) : IncrementStepCountUseCase {

    override suspend fun invoke(date: LocalDate, newSteps: Int) {
        val day = getDayUseCase(date).first()
        val updatedDay = day.copy(steps = day.steps + newSteps)
        repository.upsertDay(updatedDay)
    }
}