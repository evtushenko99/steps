package com.example.stepcounterservice

import java.time.LocalDate

data class StepCounterEvent(
    val stepCount: Int,
    val eventDate: LocalDate,
)