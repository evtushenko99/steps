package com.example.glonav

import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.designsystem.theme.StepsTheme
import com.example.localdata.model.Day
import com.example.stepcounterservice.StepCounterService
import com.example.utils.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class StepsActivity : ComponentActivity() {

    private val viewModel by viewModels<StepsViewModel>()

    @Inject
    lateinit var sensorManager: SensorManager

    private val sensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        demoDataBase()
        lifecycleScope.launch {
            viewModel.isDarkThemeEnabled.collectLatest {
                setComposeContent(it)
            }
        }


        // Multiple Permission

        val multiple =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->

                val notification = permission[POST_NOTIFICATIONS] ?: false
                val fitness = permission[ACTIVITY_RECOGNITION] ?: false

                if (notification && fitness) {
                    if (sensor != null) {
                        StepCounterService.startForegroundService(
                            context = this,
                            startActivityIntent = Intent(this, StepsActivity::class.java)
                        )
                    }
                    if (SDK_INT >= Build.VERSION_CODES.P) {
                        startActivity(Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                            data = Uri.parse("package:${packageName}")
                        })
                    }
                } else {
                    Toast.makeText(this, "Что то пошло не так", Toast.LENGTH_SHORT).show()
                }
            }

        // Single Permission

        val single =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { access ->

                if (access) {
                    if (sensor != null) {
                        StepCounterService.startForegroundService(
                            context = this,
                            startActivityIntent = Intent(this, StepsActivity::class.java)
                        )
                    }

                    if (SDK_INT >= Build.VERSION_CODES.P) {
                        startActivity(Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                            data = Uri.parse("package:${packageName}")
                        })
                    }
                } else {
                    Toast.makeText(this, "Что то пошло не так", Toast.LENGTH_SHORT).show()
                }
            }

        if (SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            multiple.launch(
                arrayOf(
                    POST_NOTIFICATIONS,
                    ACTIVITY_RECOGNITION,
                    READ_MEDIA_IMAGES,
                    READ_MEDIA_VIDEO,
                    READ_MEDIA_VISUAL_USER_SELECTED
                )
            )
        } else if (SDK_INT >= TIRAMISU) {
            multiple.launch(
                arrayOf(
                    POST_NOTIFICATIONS,
                    ACTIVITY_RECOGNITION,
                    READ_MEDIA_IMAGES,
                    READ_MEDIA_VIDEO
                )
            )
        } else {
            single.launch(ACTIVITY_RECOGNITION)
        }
    }

    private fun demoDataBase() {
        val currentDate = LocalDate.now()
        lifecycleScope.launch {
            viewModel.daysRepository.upsertDay(Day(currentDate.minusDays(6), steps = 6000))
            viewModel.daysRepository.upsertDay(Day(currentDate.minusDays(5), steps = 700))
            viewModel.daysRepository.upsertDay(Day(currentDate.minusDays(4), steps = 3500))
            viewModel.daysRepository.upsertDay(Day(currentDate.minusDays(3), steps = 630))
            viewModel.daysRepository.upsertDay(Day(currentDate.minusDays(2), steps = 10000))
            viewModel.daysRepository.upsertDay(Day(currentDate.minusDays(1), steps = 13000))
        }
    }

    private fun setComposeContent(isDarkTheme: Boolean) {
        setContent {
            val navController = rememberNavController()

            StepsTheme(isDarkTheme) {
                Scaffold(
                    bottomBar = {
                        var selectedItem = remember { mutableIntStateOf(0) }
                        val items = listOf(
                            Navigation.DAILY_COUNTER,
                            Navigation.STATISTICS,
                            Navigation.MORE
                        )
                        NavigationBar {
                            items.forEachIndexed { index, item: Navigation ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            painterResource(id = item.imageRes),
                                            contentDescription = ""
                                        )
                                    },
                                    label = { Text(item.screenTitle) },
                                    selected = selectedItem.intValue == index,
                                    onClick = {
                                        selectedItem.intValue = index
                                        navController.navigate(item.route)
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    StepsNavHost(
                        navController,
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}