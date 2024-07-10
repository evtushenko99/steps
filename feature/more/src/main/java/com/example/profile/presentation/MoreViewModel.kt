package com.example.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localdata.domain.repository.ProfileImageRepository
import com.example.localdata.domain.repository.SettingsRepository
import com.example.localdata.domain.repository.ThemeRepository
import com.example.localdata.domain.usecase.UpdateDaySettingsUseCase
import com.example.localdata.model.DaySettings
import com.example.localdata.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val updateDaySettingsUseCase: UpdateDaySettingsUseCase,
    private val themeRepository: ThemeRepository,
    private val profileImageRepository: ProfileImageRepository
) : ViewModel() {

    private var observeSettingsChangesJob: Job? = null

    private val _currentSettings = MutableStateFlow(Settings())
    private val _isDarkTheme = MutableStateFlow(false)
    private val _profileImage = MutableStateFlow("")

    val profileImage = _profileImage.asStateFlow()
    val isDarkModeTheme = _isDarkTheme.asStateFlow()
    val currentSettings: StateFlow<Settings> = _currentSettings.asStateFlow()

    fun init() {
        observeSettingsChangesJob?.cancel()
        observeSettingsChangesJob = settingsRepository.getSettingsFLow().onEach {
            _currentSettings.value = it

            updateDaySettingsUseCase(
                DaySettings(
                    date = LocalDate.now(),
                    goal = it.dailyGoal,
                    stepLength = it.stepLength,
                    height = it.height,
                    weight = it.weight,
                    pace = it.pace.value
                )
            )
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            themeRepository.isDarkTheme().collectLatest {
                _isDarkTheme.value = it
            }
        }


        viewModelScope.launch {
            profileImageRepository.getImageUrl().collectLatest {
                _profileImage.value = it
            }
        }

    }

    fun updateImageUrl(newUrl: String) {
        viewModelScope.launch {
            profileImageRepository.upsertUrl(newUrl)
        }
    }

    fun updateDailyGoal(newValue: Int) {
        settingsRepository.updateDailyGoal(newValue)
    }

    fun updateStepLength(newValue: Int) {
        settingsRepository.updateStepLength(newValue)
    }

    fun updateHeight(newValue: Int) {
        settingsRepository.updateHeight(newValue)
    }

    fun updateWeight(newValue: Int) {
        settingsRepository.updateWeight(newValue)
    }

    fun updatePace(newValue: Double) {
        settingsRepository.updatePace(newValue)
    }

    fun onThemeChanged(isDark: Boolean) {
        viewModelScope.launch {
            themeRepository.changeTheme(isDark)
        }
    }
}