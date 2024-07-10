package com.example.localdata.domain.usecase

import com.example.localdata.model.DaySettings

interface UpdateDaySettingsUseCase {
    suspend operator fun invoke(daySettings: DaySettings)
}