package com.example.statistic.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.utils.Navigation

@Composable
fun StatisticRoute(
    navController: NavHostController
) {
    val viewModel: StatisticViewModel = hiltViewModel()

    StatisticScreen(
        screenTitle = Navigation.STATISTICS.screenTitle,
        dailyState = viewModel.dailyProgress.collectAsState(),
        summaryState = viewModel.summaryStats.collectAsState(),
        weekState = viewModel.weekStats.collectAsState()
    )
}