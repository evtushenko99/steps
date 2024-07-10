package com.example.profile


import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.example.designsystem.theme.StepsTheme
import com.example.profile.presentation.MoreSettingsScreen
import com.example.localdata.model.Settings
import com.example.utils.Navigation
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class MoreSettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testChangingAllElements() {
        // Start the app
        val settings = MutableStateFlow(Settings())
        composeTestRule.apply {
            setContent {
                StepsTheme {
                    MoreSettingsScreen(
                        screenTitle = Navigation.MORE_SETTINGS.screenTitle,
                        currentSettings = settings.collectAsState(),
                        navIconClick = {},
                        onChangePaceSetting = {
                        },
                        onChangeStepLengthSetting = {
                            settings.value = settings.value.copy(stepLength = it)
                        },
                        onChangeHeightSetting = {
                            settings.value = settings.value.copy(height = it)
                        }
                    )
                }
            }

            composeTestRule.onNodeWithText(SCREEN_TITLE).assertIsDisplayed()

            composeTestRule.onNode(
                hasText(INFO_PACE_TITLE)
                        and hasText(INFO_PACE_SUBTITLE)
                        and hasText(INFO_PACE_CHEVRON)
                        and hasClickAction()
            ).assertIsDisplayed()

            testChangingSettingValue(
                title = INFO_STEP_LENGTH_TITLE,
                subTitle = INFO_STEP_LENGTH_SUBTITLE,
                oldValue = INFO_STEP_LENGTH_VALUE,
                newValue = INFO_STEP_LENGTH_NEW_VALUE
            )

            testChangingSettingValue(
                title = INFO_HEIGHT_TITLE,
                subTitle = INFO_HEIGHT_SUBTITLE,
                oldValue = INFO_HEIGHT_VALUE,
                newValue = INFO_HEIGHT_NEW_VALUE
            )
        }

    }

    private fun testChangingSettingValue(
        title: String,
        subTitle: String,
        oldValue: String,
        newValue: String
    ) {
        composeTestRule.onNode(
            hasText(title)
                    and hasText(subTitle)
                    and hasText(oldValue)
                    and hasClickAction()
        ).assertIsDisplayed().performClick()

        composeTestRule.onAllNodes(isDialog())[0].assertIsDisplayed()
        composeTestRule.onAllNodes(hasText(oldValue))[1]
            .performTextReplacement(newValue)
        composeTestRule.onNode(hasText(DIALOG_SAVE_BUTTON)).assertIsDisplayed().performClick()

        composeTestRule.onNode(
            hasText(title)
                    and hasText(subTitle)
                    and hasText(newValue)
                    and hasClickAction()
        ).assertIsDisplayed()
    }

    private companion object {
        const val SCREEN_TITLE = "Больше Настроек"
        const val INFO_PACE_TITLE = "Чувствительность"
        const val INFO_PACE_SUBTITLE =
            "Чем выше точность, тем меньшие движения будут считать за шаги"
        const val INFO_PACE_CHEVRON = "Средняя"

        const val INFO_STEP_LENGTH_TITLE = "Длина шага"
        const val INFO_STEP_LENGTH_SUBTITLE = "Нужно для расчета расстояния и скорости"
        const val INFO_STEP_LENGTH_VALUE = "70"
        const val INFO_STEP_LENGTH_NEW_VALUE = "55"

        const val INFO_HEIGHT_TITLE = "Рост"
        const val INFO_HEIGHT_SUBTITLE = "Нужно для расчета расстояния и скорости"
        const val INFO_HEIGHT_VALUE = "180"
        const val INFO_HEIGHT_NEW_VALUE = "168"

        const val DIALOG_SAVE_BUTTON = "Save"
    }
}