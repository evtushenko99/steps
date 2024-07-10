package com.example.localdata.domain.usecase

import kotlinx.coroutines.flow.Flow
import com.example.localdata.model.Day
import java.time.LocalDate

interface GetDayUseCase {

    operator fun invoke(date: LocalDate): Flow<Day>
}