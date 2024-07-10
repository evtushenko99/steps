package com.example.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.utils.Navigation

@Composable
fun MoreSettingsRoute(
    navController: NavHostController
) {
    val moreViewModel: MoreViewModel = hiltViewModel<MoreViewModel>().apply {
        init()
    }

    MoreSettingsScreen(
        screenTitle = Navigation.MORE_SETTINGS.screenTitle,
        currentSettings = moreViewModel.currentSettings.collectAsState(),
        navIconClick = { navController.navigateUp() },
        onChangePaceSetting = { moreViewModel.updatePace(it) },
        onChangeStepLengthSetting = { moreViewModel.updateStepLength(it) },
        onChangeHeightSetting = { moreViewModel.updateHeight(it) }
    )
}