package com.example.localdata.data.repository

import android.content.SharedPreferences
import com.example.localdata.domain.repository.SettingsRepository
import com.example.localdata.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SettingsRepository, SharedPreferences.OnSharedPreferenceChangeListener {

    private val settings: MutableStateFlow<Settings>

    init {
        val parsedSettings = parseSettings(sharedPreferences)
        settings = MutableStateFlow(parsedSettings)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(
        updatedSharedPreferences: SharedPreferences?,
        key: String?
    ) {
        settings.value = parseSettings(sharedPreferences)
    }

    override fun getSettingsFLow(): Flow<Settings> = settings.asStateFlow()
    override fun currentSettings(): Settings = settings.value

    override fun updateDailyGoal(newValue: Int) {
        sharedPreferences.updateValue(DAILY_GOAL_KEY, newValue.toString())
    }

    override fun updateStepLength(newValue: Int) {
        sharedPreferences.updateValue(STEP_LENGTH_KEY, newValue.toString())
    }

    override fun updateHeight(newValue: Int) {
        sharedPreferences.updateValue(HEIGHT_KEY, newValue.toString())
    }

    override fun updateWeight(newValue: Int) {
        sharedPreferences.updateValue(WEIGHT_KEY, newValue.toString())
    }

    override fun updatePace(newValue: Double) {
        sharedPreferences.updateValue(PACE_KEY, newValue.toString())
    }


    private fun parseSettings(sharedPreferences: SharedPreferences): Settings =
        sharedPreferences.run {
            Settings(
                dailyGoal = getNumericString(DAILY_GOAL_KEY, DAILY_GOAL_DEFAULT_VALUE),
                stepLength = getNumericString(STEP_LENGTH_KEY, STEP_LENGTH_DEFAULT_VALUE),
                height = getNumericString(HEIGHT_KEY, HEIGHT_DEFAULT_VALUE),
                weight = getNumericString(WEIGHT_KEY, WEIGHT_DEFAULT_VALUE),
                pace = Settings.PaceValue.fromDouble(getNumericString(PACE_KEY, PACE_DEFAULT_VALUE))
            )
        }

    private fun SharedPreferences.getNumericString(key: String, defaultValue: Int): Int =
        getString(key, "")?.toIntOrNull() ?: defaultValue

    private fun SharedPreferences.getNumericString(key: String, defaultValue: Double): Double =
        getString(key, "")?.toDoubleOrNull() ?: defaultValue

    private fun SharedPreferences.updateValue(key: String, newValue: String) {
        this.edit().apply {
            putString(key, newValue)
            apply()
        }
    }

    private companion object {
        const val DAILY_GOAL_KEY = "daily_goal"
        const val DAILY_GOAL_DEFAULT_VALUE = 7500
        const val STEP_LENGTH_KEY = "step_length"
        const val STEP_LENGTH_DEFAULT_VALUE = 70
        const val HEIGHT_KEY = "height"
        const val HEIGHT_DEFAULT_VALUE = 188
        const val WEIGHT_KEY = "weight"
        const val WEIGHT_DEFAULT_VALUE = 88
        const val PACE_KEY = "pace"
        const val PACE_DEFAULT_VALUE = 1.0
    }
}