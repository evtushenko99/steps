package com.example.localdata.model

import com.example.localdata.model.DbConstants.DAILY_GOAL_DEFAULT_VALUE
import com.example.localdata.model.DbConstants.HEIGHT_DEFAULT_VALUE
import com.example.localdata.model.DbConstants.PACE_NORMAL_VALUE
import com.example.localdata.model.DbConstants.STEP_LENGTH_DEFAULT_VALUE
import com.example.localdata.model.DbConstants.WEIGHT_DEFAULT_VALUE
import java.time.LocalDate

data class DaySettings(
    val date: LocalDate,
    val goal: Int = DAILY_GOAL_DEFAULT_VALUE,
    val stepLength: Int = STEP_LENGTH_DEFAULT_VALUE,
    val height: Int = HEIGHT_DEFAULT_VALUE,
    val weight: Int = WEIGHT_DEFAULT_VALUE,
    val pace: Double = PACE_NORMAL_VALUE
)
