package com.example.profile.presentation

import com.example.localdata.domain.repository.ProfileImageRepository
import com.example.localdata.domain.repository.SettingsRepository
import com.example.localdata.domain.repository.ThemeRepository
import com.example.localdata.domain.usecase.UpdateDaySettingsUseCase
import com.example.localdata.model.DaySettings
import com.example.localdata.model.Settings
import com.example.test.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class MoreViewModelTest {

    @JvmField
    @Rule
    val coroutineRule = MainCoroutineRule()

    private val settingsFLow = MutableStateFlow(Settings())
    private val darkThemeFlow = MutableStateFlow(true)
    private val imageUrlFlow = MutableStateFlow(PROFILE_IMAGE_URL)

    private val settingsRepository: SettingsRepository = mockk()
    private val updateDaySettingsUseCase: UpdateDaySettingsUseCase = mockk()
    private val themeRepository: ThemeRepository = mockk()
    private val profileImageRepository: ProfileImageRepository = mockk()

    private lateinit var viewModel: MoreViewModel

    @Before
    fun setUp() {
        every { settingsRepository.getSettingsFLow() } returns settingsFLow
        every { themeRepository.isDarkTheme() } returns darkThemeFlow
        coEvery { profileImageRepository.getImageUrl() } returns imageUrlFlow

        viewModel = MoreViewModel(
            settingsRepository,
            updateDaySettingsUseCase,
            themeRepository,
            profileImageRepository
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Assert init`() = runTest(coroutineRule.testDispatcher) {
        // Arrange
        val actualSettingsResult = mutableListOf<Settings>()
        val actualThemeResult = mutableListOf<Boolean>()
        val actualImageResult = mutableListOf<String>()

        coEvery {
            updateDaySettingsUseCase(
                DaySettings(
                    date = LocalDate.now(),
                    goal = DAILY_GOAL_DEFAULT_VALUE,
                    stepLength = STEP_LENGTH_DEFAULT_VALUE,
                    height = HEIGHT_DEFAULT_VALUE,
                    weight = WEIGHT_DEFAULT_VALUE,
                    pace = PACE_DEFAULT_VALUE
                )
            )
        } returns Unit

        backgroundScope.launch {
            viewModel.currentSettings.toList(actualSettingsResult)
        }

        backgroundScope.launch {
            viewModel.isDarkModeTheme.toList(actualThemeResult)
        }

        backgroundScope.launch {
            viewModel.profileImage.toList(actualImageResult)
        }

        val expectedSettingsResult = listOf(Settings())
        val expectedThemeResult = listOf(false, true)
        val expectedImageResult = listOf("", PROFILE_IMAGE_URL)

        // Act
        viewModel.init()

        // Assert
        assertThat(actualSettingsResult).isEqualTo(expectedSettingsResult)
        assertThat(actualThemeResult).isEqualTo(expectedThemeResult)
        assertThat(actualImageResult).isEqualTo(expectedImageResult)

        coVerify {
            settingsRepository.getSettingsFLow()
            updateDaySettingsUseCase(
                DaySettings(
                    date = LocalDate.now(),
                    goal = DAILY_GOAL_DEFAULT_VALUE,
                    stepLength = STEP_LENGTH_DEFAULT_VALUE,
                    height = HEIGHT_DEFAULT_VALUE,
                    weight = WEIGHT_DEFAULT_VALUE,
                    pace = Settings.PaceValue.NORMAL.value
                )
            )
            themeRepository.isDarkTheme()
            profileImageRepository.getImageUrl()
        }
    }

    @Test
    fun `Assert updateImageUrl`() {
        // Arrange
        coJustRun { profileImageRepository.upsertUrl(PROFILE_IMAGE_URL) }

        // Act
        viewModel.updateImageUrl(PROFILE_IMAGE_URL)

        // Assert
        coVerifySequence { profileImageRepository.upsertUrl(PROFILE_IMAGE_URL) }
    }

    @Test
    fun `Assert updateDailyGoal`() {
        // Arrange
        justRun { settingsRepository.updateDailyGoal(DAILY_GOAL_VALUE) }

        // Act
        viewModel.updateDailyGoal(DAILY_GOAL_VALUE)

        // Assert
        verifySequence { settingsRepository.updateDailyGoal(DAILY_GOAL_VALUE) }
    }

    @Test
    fun `Assert updateStepLength`() {
        // Arrange
        justRun { settingsRepository.updateStepLength(STEP_LENGTH_VALUE) }

        // Act
        viewModel.updateStepLength(STEP_LENGTH_VALUE)

        // Assert
        verifySequence { settingsRepository.updateStepLength(STEP_LENGTH_VALUE) }
    }

    @Test
    fun `Assert updateHeight`() {
        // Arrange
        justRun { settingsRepository.updateHeight(HEIGHT_VALUE) }

        // Act
        viewModel.updateHeight(HEIGHT_VALUE)

        // Assert
        verifySequence { settingsRepository.updateHeight(HEIGHT_VALUE) }
    }

    @Test
    fun `Assert updateWeight`() {
        // Arrange
        justRun { settingsRepository.updateWeight(WEIGHT_VALUE) }

        // Act
        viewModel.updateWeight(WEIGHT_VALUE)

        // Assert
        verifySequence { settingsRepository.updateWeight(WEIGHT_VALUE) }
    }

    @Test
    fun `Assert updatePace`() {
        // Arrange
        justRun { settingsRepository.updatePace(PACE_VALUE) }

        // Act
        viewModel.updatePace(PACE_VALUE)

        // Assert
        verifySequence { settingsRepository.updatePace(PACE_VALUE) }
    }

    @Test
    fun `Assert onThemeChanged`() {
        // Arrange
        coJustRun { themeRepository.changeTheme(true) }

        // Act
        viewModel.onThemeChanged(true)

        // Assert
        coVerifySequence { themeRepository.changeTheme(true) }
    }

    private companion object {
        const val DAILY_GOAL_DEFAULT_VALUE = 7500
        const val DAILY_GOAL_VALUE = 666
        const val STEP_LENGTH_DEFAULT_VALUE = 70
        const val STEP_LENGTH_VALUE = 55
        const val HEIGHT_DEFAULT_VALUE = 180
        const val HEIGHT_VALUE = 161
        const val WEIGHT_DEFAULT_VALUE = 70
        const val WEIGHT_VALUE = 99
        const val PACE_DEFAULT_VALUE = 1.0
        const val PACE_VALUE = 1.2
        const val PROFILE_IMAGE_URL = "imageUrl"
    }
}