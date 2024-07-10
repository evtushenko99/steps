package com.example.localdata.domain.usecase

import com.example.localdata.domain.repository.DaysRepository
import com.example.localdata.model.DaySettings
import javax.inject.Inject

class UpdateDaySettingsUseCaseImpl @Inject constructor(
    private val dayRepository: DaysRepository
) : UpdateDaySettingsUseCase {

    override suspend fun invoke(daySettings: DaySettings) {
        dayRepository.updateDaySettings(daySettings)
    }
}
