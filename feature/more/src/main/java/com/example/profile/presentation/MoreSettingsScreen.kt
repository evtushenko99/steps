package com.example.profile.presentation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.designsystem.DSCard
import com.example.designsystem.DSScaffold
import com.example.designsystem.InfoElementModifierForDsCard
import com.example.designsystem.InfoElementPrimary
import com.example.designsystem.InfoElementWithDialog
import com.example.localdata.model.Settings
import com.example.utils.R

@Composable
fun MoreSettingsScreen(
    screenTitle: String,
    currentSettings: State<Settings>,
    navIconClick: () -> Unit,
    onChangePaceSetting: (Double) -> Unit,
    onChangeStepLengthSetting:(Int) -> Unit,
    onChangeHeightSetting: (Int) -> Unit
) {
    DSScaffold(screenTitle, navIconClick) { paddingValues ->
        DSCard(Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

                BoxWithConstraints {
                    var openPaceMenu by remember { mutableStateOf(false) }

                    InfoElementPrimary(
                        modifier = InfoElementModifierForDsCard,
                        title = stringResource(id = R.string.setting_pace_title),
                        subTitle = stringResource(id = R.string.setting_pace_subtitle),
                        chevronValue = stringResource(id = currentSettings.value.pace.stringRes),
                        iconRes = R.drawable.ic_statistics_24
                    ) { openPaceMenu = true }

                    DropdownMenu(
                        expanded = openPaceMenu,
                        onDismissRequest = { openPaceMenu = false },
                        offset = DpOffset(this.maxWidth, 0.dp),
                    ) {
                        Settings.PaceValue.entries.forEach {
                            DropdownMenuItem(
                                onClick =  {
                                    onChangePaceSetting(it.value)
                                    openPaceMenu = false
                                },
                                text = { Text(stringResource(id = it.stringRes)) }
                            )
                        }
                    }
                }

                InfoElementWithDialog(
                    modifier = InfoElementModifierForDsCard,
                    title = stringResource(id = R.string.setting_step_length_title),
                    subTitle = stringResource(id = R.string.setting_step_length_height_subtitle),
                    value = currentSettings.value.stepLength.toString(),
                    iconRes = R.drawable.ic_steps_24
                ) { onChangeStepLengthSetting(it.toInt()) }

                InfoElementWithDialog(
                    modifier = InfoElementModifierForDsCard,
                    title = stringResource(id = R.string.setting_height_title),
                    subTitle = stringResource(id = R.string.setting_step_length_height_subtitle),
                    value = currentSettings.value.height.toString(),
                    iconRes = R.drawable.ic_tree_24
                ) { onChangeHeightSetting(it.toInt()) }
            }
        }

    }
}