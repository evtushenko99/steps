package com.example.stepcounterservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.utils.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class StepCounterService : LifecycleService(), SensorEventListener {

    private lateinit var notificationManager: NotificationManager
    private lateinit var launchActivityPendingIntent: PendingIntent

    @Inject
    lateinit var sensorManager: SensorManager


    @Inject
    lateinit var controller: StepCounterController


    override fun onCreate() {
        super.onCreate()
        resolveDependencies()

        createNotificationChannel()
        registerStepCounter()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createContentPendingIntent(intent)

        ServiceCompat.startForeground(
            /* service = */ this,
            /* id = */ NOTIFICATION_ID,
            /* notification = */ createNotification(controller.stats.value),
            /* foregroundServiceType = */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_HEALTH
            } else {
                0
            }
        )

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                controller.stats.collect {
                    val updatedNotification = createNotification(it)
                    notificationManager.notify(NOTIFICATION_ID, updatedNotification)
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }


    private fun createContentPendingIntent(intent: Intent?) {
        val launchActivityIntent = (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(EXTRA_ACTIVITY_INTENT, Intent::class.java)
        } else {
            intent?.getParcelableExtra(EXTRA_ACTIVITY_INTENT) as? Intent

        }) ?: throw RuntimeException("launchActivityPendingIntent doesnot exist")

        launchActivityPendingIntent = PendingIntent.getActivity(
            this,
            PENDING_INTENT_ID,
            launchActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val eventStepCount = it.values[0].toInt()
            controller.onStepCountChanged(eventStepCount, LocalDate.now())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        controller.destroy()

        super.onDestroy()
    }

    private fun resolveDependencies() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun createNotification(state: StepCounterState): Notification = state.run {
        val title = "$steps ${resources.getString(R.string.stat_steps_taken)}"
        val progress = if (goal == 0) 0 else steps * 100 / goal
        val content = resources.getString(
            R.string.service_content_text,
            calorieBurned.toString(),
            distanceTravelled.toString(),
            progress.toString()
        )

        NotificationCompat.Builder(this@StepCounterService, NOTIFICATION_CHANNEL_ID)
            .setContentIntent(launchActivityPendingIntent)
            .setSmallIcon(R.drawable.ic_tree_24)
            .setContentTitle(title)
            .setContentText(content)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSilent(true)
            .build()
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.service_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
            setShowBadge(false)
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun registerStepCounter() {
        val stepCounterSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        stepCounterSensor?.let {
            sensorManager.registerListener(
                /* listener = */ this,
                /* sensor = */ it,
                /* samplingPeriodUs = */ SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "step_counter_channel"
        private const val NOTIFICATION_ID = 0x1
        private const val PENDING_INTENT_ID = 0x1

        private const val EXTRA_ACTIVITY_INTENT = "startActivityIntent"

        fun startForegroundService(context: Context, startActivityIntent: Intent) {
            val intent = Intent(context, StepCounterService::class.java).apply {
                putExtra(EXTRA_ACTIVITY_INTENT, startActivityIntent)
            }

            ContextCompat.startForegroundService(context, intent)
        }

    }

}