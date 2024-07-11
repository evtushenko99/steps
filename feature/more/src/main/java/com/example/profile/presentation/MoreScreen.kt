package com.example.profile.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.designsystem.DSCard
import com.example.designsystem.DSRowWithRadioButton
import com.example.designsystem.DSScaffold
import com.example.designsystem.InfoElementModifierForDsCard
import com.example.designsystem.InfoElementPrimary
import com.example.designsystem.InfoElementWithDialog
import com.example.designsystem.theme.BorderWidth
import com.example.designsystem.theme.Paddings
import com.example.designsystem.theme.StepsTheme
import com.example.localdata.model.Settings
import com.example.utils.R
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
internal fun MoreScreen(
    screenTitle: String,
    currentSettings: State<Settings>,
    themeState: State<Boolean>,
    imageUrlState: State<String>,
    onChangeDailyGoalSetting: (Int) -> Unit,
    onChangeWeightSetting: (Int) -> Unit,
    onThemeChanged: (Boolean) -> Unit,
    onUpdateImageUrl: (String) -> Unit,
    moreSettingsClick: () -> Unit
) {
    DSScaffold(screenTitle) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            DSCard {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    ProfileImage(
                        profileImageUrl = imageUrlState,
                        updateImageUrl = { onUpdateImageUrl(it) }
                    )

                    InfoElementWithDialog(
                        modifier = InfoElementModifierForDsCard,
                        title = stringResource(id = R.string.setting_daily_goal_title),
                        value = currentSettings.value.dailyGoal.toString(),
                        iconRes = R.drawable.ic_goal_24
                    ) {
                        onChangeDailyGoalSetting(it.toInt())
                    }

                    InfoElementWithDialog(
                        modifier = InfoElementModifierForDsCard,
                        title = stringResource(id = R.string.setting_weight_title),
                        subTitle = stringResource(id = R.string.setting_weight_subtitle),
                        value = currentSettings.value.weight.toString(),
                        iconRes = R.drawable.ic_monitor_weight_24
                    ) {
                        onChangeWeightSetting(it.toInt())
                    }


                    InfoElementPrimary(
                        modifier = InfoElementModifierForDsCard,
                        title = stringResource(id = R.string.setting_more_title),
                        subTitle = stringResource(id = R.string.setting_more_subtitle),
                        iconRes = R.drawable.ic_settings_24
                    ) { moreSettingsClick() }
                }
            }

            DSCard {
                DSRowWithRadioButton(
                    title = stringResource(id = R.string.use_dark_mode),
                    modifier = Modifier,
                    buttonState = themeState.value,
                    onSwitchClick = { onThemeChanged(it) }
                )
            }
        }

    }
}


@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    profileImageUrl: State<String>,
    updateImageUrl: (String) -> Unit
) {
    val imageUri = remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            updateImageUrl(uri.toString())
            imageUri.value = it.toString()
        }
    }
    Box(
        modifier = Modifier
            .padding(start = Paddings.medium, end = Paddings.medium, top = Paddings.small)
            .fillMaxWidth()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        val defaultPainter = painterResource(id = R.drawable.ic_person_24)
        Card(
            modifier = Modifier
                .padding(Paddings.small)
                .size(128.dp, 128.dp),
            shape = CircleShape,
            border = BorderStroke(width = BorderWidth.small, MaterialTheme.colorScheme.onPrimary),
        ) {
            if (imageUri.value.isNotBlank()) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageUri.value)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { launcher.launch("image/*") },
                    contentScale = ContentScale.Crop,
                    error = defaultPainter
                )
            } else {
                Image(
                    painter = defaultPainter,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(Paddings.medium)
                        .clip(CircleShape)
                        .fillMaxSize()
                        .clickable { launcher.launch("image/*") },
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileImage() {
    StepsTheme {
        ProfileImage(
            profileImageUrl = MutableStateFlow("").collectAsState(),
            updateImageUrl = {}
        )
    }
}