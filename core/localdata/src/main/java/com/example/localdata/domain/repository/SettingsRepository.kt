package com.example.localdata.domain.repository

import com.example.localdata.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettingsFLow(): Flow<Settings>
    fun currentSettings(): Settings
    fun updateDailyGoal(newValue: Int)
    fun updateStepLength(newValue: Int)
    fun updateHeight(newValue: Int)
    fun updateWeight(newValue: Int)
    fun updatePace(newValue: Double)
}