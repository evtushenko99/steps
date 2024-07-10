package com.example.utils.model

import java.time.LocalDate

data class BarChartWeek(
    val week: List<BarChartDay> = listOf(),
    val dailyGoal: Int = 0
)
data class BarChartDay(
    val date: LocalDate = LocalDate.now(),
    val steps: Int = 0,
    val dailyGoal: Int = 7500
)