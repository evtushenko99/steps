package com.example.utils.model

/**
 * Состояние пользователя в разрезе дня
 *
 * @param stepsTaken пройдено шагов
 * @param dailyGoal цель на день (какое кол-во шагов нужно пройти)
 * @param calorieBurned каллорий сожжено (относительно пройденных шагов)
 * @param distanceTravelled пройдено км (относительно пройденных шагов)
 * @param carbonDioxideSaved сохранено углекислого газа (относительно пройденных шагов)
 */
data class DailyCounterState(
    val stepsTaken: Int = 0,
    val dailyGoal: Int = 0,
    val calorieBurned: Double = 0.0,
    val distanceTravelled: Double = 0.0,
    val carbonDioxideSaved: Double = 0.0,
)
