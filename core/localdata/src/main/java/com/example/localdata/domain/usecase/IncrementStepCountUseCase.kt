package com.example.localdata.domain.usecase

import java.time.LocalDate

interface IncrementStepCountUseCase {

    suspend operator fun invoke(date: LocalDate, newSteps: Int)
}