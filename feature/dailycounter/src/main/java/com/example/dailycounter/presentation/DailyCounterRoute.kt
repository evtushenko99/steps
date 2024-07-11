package com.example.dailycounter.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.utils.Navigation

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