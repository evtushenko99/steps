package com.example.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.utils.Navigation

@Composable
fun MoreRoute(
    navController: NavHostController
) {
    val moreViewModel: MoreViewModel = hiltViewModel<MoreViewModel>().apply {
        init()
    }

    MoreScreen(
        screenTitle = Navigation.MORE.screenTitle,
        currentSettings = moreViewModel.currentSettings.collectAsState(),
        themeState = moreViewModel.isDarkModeTheme.collectAsState(),
        imageUrlState = moreViewModel.profileImage.collectAsState(),
        onChangeDailyGoalSetting = { moreViewModel.updateDailyGoal(it) },
        onChangeWeightSetting = { moreViewModel.updateWeight(it) },
        onThemeChanged = { moreViewModel.onThemeChanged(it) },
        onUpdateImageUrl = { moreViewModel.updateImageUrl(it) }
    ) { navController.navigate(Navigation.MORE_SETTINGS.route) }
}