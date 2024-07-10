package com.example.localdata.domain.usecase

import com.example.localdata.domain.repository.DaysRepository
import com.example.localdata.domain.repository.SettingsRepository
import com.example.localdata.model.Day
import com.example.localdata.model.of
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class GetDayUseCaseImpl @Inject constructor(
    private val dayRepository: DaysRepository,
    private val settingsRepository: SettingsRepository
) : GetDayUseCase {

    override fun invoke(date: LocalDate): Flow<Day> {
        val settingsFlow = settingsRepository.getSettingsFLow()
        val dayFlow = dayRepository.getDay(date)

        return settingsFlow.combine(dayFlow) { settings, day ->
            day ?: Day.of(date, settings)
        }
    }
}