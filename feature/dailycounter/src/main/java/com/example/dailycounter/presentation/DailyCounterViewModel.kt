package com.example.dailycounter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localdata.domain.usecase.GetDayUseCase
import com.example.utils.model.DailyCounterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DailyCounterViewModel @Inject constructor(
    private val getDayUseCase: GetDayUseCase,
    private val currentDateFlow: StateFlow<LocalDate>
) : ViewModel() {
    private val _progress = MutableStateFlow(DailyCounterState())
    val progress: StateFlow<DailyCounterState> = _progress.asStateFlow()

    private var getProgressJob: Job? = null

    init {
        viewModelScope.launch {
            currentDateFlow.collect { date ->
                getProgress(date)
            }
        }
    }


    private fun getProgress(date: LocalDate) {
        getProgressJob?.cancel()

        getProgressJob = getDayUseCase(date).onEach { day ->
            _progress.value = progress.value.copy(
                stepsTaken = day.steps,
                dailyGoal = day.goal,
                calorieBurned = day.calorieBurned,
                distanceTravelled = day.distanceTravelled,
                carbonDioxideSaved = day.carbonDioxideSaved,
            )
        }.launchIn(viewModelScope)
    }
}