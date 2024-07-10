package com.example.steps

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

@HiltAndroidApp
class StepsApplication : Application() {

    val currentDate = MutableStateFlow<LocalDate>(LocalDate.now())

    override fun onCreate() {
        super.onCreate()
        registerMidnightTimer()
    }

    private fun registerMidnightTimer() {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
        }
        registerReceiver(midnightBroadcastReceiver, intentFilter)
    }

    private val midnightBroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val today = LocalDate.now()
            if (today != currentDate.value) {
                currentDate.value = today
            }
        }
    }
}