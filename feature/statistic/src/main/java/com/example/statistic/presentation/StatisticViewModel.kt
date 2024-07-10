package com.example.statistic.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localdata.domain.repository.DaysRepository
import com.example.localdata.domain.usecase.GetDayUseCase
import com.example.localdata.model.StatsSummary
import com.example.localdata.model.of
import com.example.statistic.presentation.model.SummaryStatsState
import com.example.utils.model.BarChartDay
import com.example.utils.model.BarChartWeek
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

/**
 * TODO - Описание класса
 *
 * @author Евтушенко Максим 30.06.2024
 */
@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getDayUseCase: GetDayUseCase,
    private val daysRepository: DaysRepository,
    private val currentDateFlow: StateFlow<LocalDate>
) : ViewModel() {
    private val _dailyProgress = MutableStateFlow(DailyCounterState())
    val dailyProgress: StateFlow<DailyCounterState> = _dailyProgress.asStateFlow()

    private val _summaryStats = MutableStateFlow(SummaryStatsState())
    val summaryStats: StateFlow<SummaryStatsState> = _summaryStats.asStateFlow()

    private val _weekStats = MutableStateFlow(BarChartWeek())
    val weekStats: StateFlow<BarChartWeek> = _weekStats.asStateFlow()

    private var dailyProgressJob: Job? = null
    private var refreshSummaryStatsJob: Job? = null
    private var weekStatsJob: Job? = null

    init {
        viewModelScope.launch {
            currentDateFlow.collect { date ->
                getDailyProgress(date)
            }
        }
    }

    private fun refreshStatsSummary() {
        refreshSummaryStatsJob?.cancel()
        refreshSummaryStatsJob = viewModelScope.launch {
            val updatedSummary = StatsSummary.of(daysRepository.getAllDays())
            updatedSummary.run {
                _summaryStats.value = summaryStats.value.copy(
                    treesCollected = treesCollected,
                    stepsTaken = stepsTaken,
                    calorieBurned = calorieBurned,
                    distanceTravelled = distanceTravelled,
                    carbonDioxideSaved = carbonDioxideSaved,
                )
            }
        }
    }

    private fun refreshWeekStats(lastDay: LocalDate) {
        weekStatsJob?.cancel()
        weekStatsJob = viewModelScope.launch {
            val firstDay = lastDay.minusDays(6)
            daysRepository.getDays(firstDay..lastDay).collect { week ->
                if (week.isNotEmpty()) {
                    val uiWeek = week.map {
                        BarChartDay(it.date, it.steps, it.goal)
                    }
                    _weekStats.value = BarChartWeek(uiWeek, week.first().goal)
                }
            }
        }
    }

    private fun getDailyProgress(date: LocalDate) {
        dailyProgressJob?.cancel()
        dailyProgressJob = getDayUseCase(date).onEach { day ->
            _dailyProgress.value = DailyCounterState(
                stepsTaken = day.steps,
                dailyGoal = day.goal,
                calorieBurned = day.calorieBurned,
                distanceTravelled = day.distanceTravelled,
                carbonDioxideSaved = day.carbonDioxideSaved,
            )
            refreshStatsSummary()
            refreshWeekStats(LocalDate.now())
        }.launchIn(viewModelScope)
    }
}