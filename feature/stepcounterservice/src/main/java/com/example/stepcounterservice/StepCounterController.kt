package com.example.stepcounterservice

import com.example.localdata.domain.usecase.GetDayUseCase
import com.example.localdata.domain.usecase.IncrementStepCountUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

class StepCounterController @Inject constructor(
    private val getDayUseCase: GetDayUseCase,
    private val incrementStepCountUseCase: IncrementStepCountUseCase,
    private val currentDateFlow: StateFlow<LocalDate>,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val _stats = MutableStateFlow(StepCounterState(LocalDate.now(), 0, 0, 0.0, 0))

    private val rawStepSensorReadings = MutableStateFlow(StepCounterEvent(0, LocalDate.MIN))
    private var stepCount: Int = 0
    private var previousStepCount: Int = 0

    val stats: StateFlow<StepCounterState> = _stats.asStateFlow()

    private var getStatsJob: Job? = null

    init {
        coroutineScope.launch {
            currentDateFlow.collect { getStats(it) }
        }
        coroutineScope.launch(Dispatchers.IO) {
            rawStepSensorReadings.collect { event ->
                val stepCountDifference = event.stepCount - previousStepCount
                previousStepCount = event.stepCount
                incrementStepCountUseCase.invoke(event.eventDate, stepCountDifference)
            }
        }
    }

    fun onStepCountChanged(newStep: Int, eventDate: LocalDate) {
        stepCount += newStep
        rawStepSensorReadings.value = StepCounterEvent(stepCount, eventDate)
    }

    fun destroy() {
        coroutineScope.cancel()
    }

    private fun getStats(date: LocalDate) {
        getStatsJob?.cancel()

        getStatsJob = coroutineScope.launch {
            getDayUseCase.invoke(date).collect { day ->
                _stats.value = day.run {
                    StepCounterState(
                        date = date,
                        steps = steps,
                        goal = goal,
                        distanceTravelled = distanceTravelled,
                        calorieBurned = calorieBurned.roundToInt()
                    )
                }
            }
        }

    }
}
