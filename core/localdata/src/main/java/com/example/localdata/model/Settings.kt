package com.example.localdata.model

import androidx.annotation.StringRes
import com.example.localdata.model.DbConstants.DAILY_GOAL_DEFAULT_VALUE
import com.example.localdata.model.DbConstants.HEIGHT_DEFAULT_VALUE
import com.example.localdata.model.DbConstants.PACE_NORMAL_VALUE
import com.example.localdata.model.DbConstants.STEP_LENGTH_DEFAULT_VALUE
import com.example.localdata.model.DbConstants.WEIGHT_DEFAULT_VALUE

data class Settings(
    val dailyGoal: Int = DAILY_GOAL_DEFAULT_VALUE,
    val stepLength: Int = STEP_LENGTH_DEFAULT_VALUE,
    val height: Int = HEIGHT_DEFAULT_VALUE,
    val weight: Int = WEIGHT_DEFAULT_VALUE,
    val pace: PaceValue = PaceValue.NORMAL
) {

    enum class PaceValue(val value: Double, @StringRes val stringRes: Int) {
        SLOW(
            value = 0.8,
            stringRes = com.example.utils.R.string.setting_pace_slow_value
        ),
        NORMAL(
            value = PACE_NORMAL_VALUE,
            stringRes = com.example.utils.R.string.setting_pace_normal_value
        ),
        FAST(
            value = 1.2,
            stringRes = com.example.utils.R.string.setting_pace_fast_value
        );

        companion object {
            fun fromDouble(value: Double): PaceValue = entries.find { it.value == value } ?: NORMAL
        }
    }
}
