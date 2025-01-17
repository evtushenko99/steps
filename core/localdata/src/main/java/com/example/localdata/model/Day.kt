package com.example.localdata.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.localdata.model.DbConstants.DAILY_GOAL_DEFAULT_VALUE
import com.example.localdata.model.DbConstants.DAYS_TABLE_NAME
import com.example.localdata.model.DbConstants.HEIGHT_DEFAULT_VALUE
import com.example.localdata.model.DbConstants.PACE_NORMAL_VALUE
import com.example.localdata.model.DbConstants.STEP_LENGTH_DEFAULT_VALUE
import com.example.localdata.model.DbConstants.WEIGHT_DEFAULT_VALUE
import java.math.RoundingMode
import java.time.LocalDate

@Entity(tableName = DAYS_TABLE_NAME)
data class Day(
    @PrimaryKey val date: LocalDate,
    val steps: Int = 0,
    val goal: Int = DAILY_GOAL_DEFAULT_VALUE,
    val stepLength: Int = STEP_LENGTH_DEFAULT_VALUE,
    val height: Int = HEIGHT_DEFAULT_VALUE,
    val weight: Int = WEIGHT_DEFAULT_VALUE,
    val pace: Double = PACE_NORMAL_VALUE
) {

    companion object

    val distanceTravelled: Double
        get() = ((steps * stepLength).toDouble() / 100_000).round()

    val calorieBurned: Double
        get() {
            val modifier = height / 182.0 + weight / 70.0 - 1
            return (0.04 * steps * pace * modifier).round()
        }

    val carbonDioxideSaved
        get() = (steps * 0.1925 / 1000.0).round()


    private fun Double.round() = this.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
}

fun Day.Companion.of(date: LocalDate, settings: Settings, steps: Int = 0): Day {
    return settings.run {
        Day(
            date = date,
            steps = steps,
            goal = dailyGoal,
            height = height,
            weight = weight,
            stepLength = stepLength,
            pace = pace.value
        )
    }
}