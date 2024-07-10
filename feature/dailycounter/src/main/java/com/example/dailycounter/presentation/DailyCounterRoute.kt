package com.example.dailycounter.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.utils.Navigation
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

/**
 * TODO - Описание класса
 *
 * @author Евтушенко Максим 22.06.2024
 */
@Composable
fun DailyCounterRoute(
    navController: NavHostController
) {
    val dailyViewModel: DailyCounterViewModel = hiltViewModel()

    DailyCounterScreen(
        screenTitle = Navigation.DAILY_COUNTER.screenTitle,
        state = dailyViewModel.progress.collectAsState()
    )
}