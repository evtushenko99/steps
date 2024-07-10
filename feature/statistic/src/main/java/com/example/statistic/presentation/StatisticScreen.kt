package com.example.statistic.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.DSBarChart
import com.example.designsystem.DSScaffold
import com.example.designsystem.DefaultInfoElementModifier
import com.example.designsystem.InfoElementPrimary
import com.example.designsystem.InfoElementSecondary
import com.example.designsystem.InfoElementTertiary
import com.example.designsystem.theme.Paddings
import com.example.designsystem.theme.StepsTheme
import com.example.statistic.R
import com.example.statistic.presentation.model.SummaryStatsState
import com.example.utils.model.BarChartWeek
import com.example.utils.model.DailyCounterState
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Экран статистики клиента
 *
 * @author Евтушенко Максим 20.05.2024
 */
@Composable
internal fun StatisticScreen(
    screenTitle: String,
    dailyState: State<DailyCounterState>,
    summaryState: State<SummaryStatsState>,
    weekState: State<BarChartWeek>
) {
    DSScaffold(screenTitle) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            val tabs = listOf(StatisticScreenTabType.DETAILS, StatisticScreenTabType.SUMMARY)
            val chosenTab = remember { mutableStateOf(StatisticScreenTabType.DETAILS) }

            StepsTabs(
                tabs,
                chosenTab.value,
                { chosenTab.value = it },
                true
            )

            when (chosenTab.value) {
                StatisticScreenTabType.DETAILS -> {
                    DetailsStatisticScreen(dailyState = dailyState)
                }

                StatisticScreenTabType.SUMMARY -> {
                    SummaryStatisticScreen(
                        summaryState = summaryState,
                        weekState = weekState
                    )
                }
            }

        }
    }
}

@Composable
private fun StepsTabs(
    categories: List<StatisticScreenTabType>,
    selectedCategory: StatisticScreenTabType,
    onCategorySelected: (StatisticScreenTabType) -> Unit,
    showHorizontalLine: Boolean,
    modifier: Modifier = Modifier,
) {
    if (categories.isEmpty()) {
        return
    }

    val selectedIndex = categories.indexOfFirst { it == selectedCategory }

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = Color.Transparent,
        indicator = { StepsTabIndicator(it, selectedIndex) },
        modifier = modifier,
        divider = {
            if (showHorizontalLine) {
                HorizontalDivider()
            }
        }
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onCategorySelected(category) },
                text = {
                    Text(
                        text = when (category) {
                            StatisticScreenTabType.DETAILS -> stringResource(R.string.statistic_screen_tab_details)
                            StatisticScreenTabType.SUMMARY -> stringResource(R.string.statistic_screen_tab_summary)
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            )
        }
    }
}

enum class StatisticScreenTabType {
    DETAILS, SUMMARY
}

@Composable
private fun StepsTabIndicator(
    tabPositions: List<TabPosition>,
    selectedIndex: Int,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Spacer(
        Modifier
            .tabIndicatorOffset(tabPositions[selectedIndex])
            .padding(horizontal = Paddings.xmedium)
            .height(4.dp)
            .background(color, RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
    )
}

@Composable
private fun SummaryStatisticScreen(
    summaryState: State<SummaryStatsState>,

    weekState: State<BarChartWeek>
) {
    BoxWithConstraints {
        val boxWithConstraintsScope = this
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (weekState.value.week.isNotEmpty()) {
                DSBarChart(
                    weekState = weekState,
                    parentWidth = boxWithConstraintsScope.constraints.maxWidth,
                    animated = true
                )
            }

            InfoElementTertiary(
                modifier = DefaultInfoElementModifier,
                title = "${summaryState.value.treesCollected} деревьев",
                subTitle = stringResource(id = com.example.utils.R.string.stat_trees_collected),
                iconRes = com.example.utils.R.drawable.ic_forest_24
            )
            InfoElementPrimary(
                modifier = DefaultInfoElementModifier,
                title = "${summaryState.value.stepsTaken} шагов",
                subTitle = stringResource(id = com.example.utils.R.string.stat_steps_taken),
                iconRes = com.example.utils.R.drawable.ic_steps_24
            )
            InfoElementSecondary(
                modifier = DefaultInfoElementModifier,
                title = "${summaryState.value.calorieBurned} ккал",
                subTitle = stringResource(id = com.example.utils.R.string.stat_calorie_burned_subtitle),
            )
            InfoElementTertiary(
                modifier = DefaultInfoElementModifier,
                title = "${summaryState.value.distanceTravelled} км",
                subTitle = stringResource(id = com.example.utils.R.string.stat_distanced_traveled_subtitle),
            )
            InfoElementPrimary(
                modifier = DefaultInfoElementModifier,
                title = "${summaryState.value.carbonDioxideSaved} кг",
                subTitle = stringResource(id = com.example.utils.R.string.stat_carbon_dioxide_saved_subtitle),
                iconRes = com.example.utils.R.drawable.ic_bubble_chart_fill_24
            )
        }
    }
}

@Composable
private fun DetailsStatisticScreen(
    dailyState: State<DailyCounterState>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        InfoElementPrimary(
            modifier = DefaultInfoElementModifier,
            title = dailyState.value.stepsTaken.toString(),
            subTitle = stringResource(id = com.example.utils.R.string.stat_steps_taken),
            iconRes = com.example.utils.R.drawable.ic_steps_24
        )
        InfoElementSecondary(
            modifier = DefaultInfoElementModifier,
            title = stringResource(
                com.example.utils.R.string.stat_calorie_burned_title,
                dailyState.value.calorieBurned
            ),
            subTitle = stringResource(
                id = com.example.utils.R.string.stat_calorie_burned_subtitle
            )
        )
        InfoElementTertiary(
            modifier = DefaultInfoElementModifier,
            title = stringResource(
                com.example.utils.R.string.stat_distanced_traveled_title,
                dailyState.value.distanceTravelled
            ),
            subTitle = stringResource(
                id = com.example.utils.R.string.stat_distanced_traveled_subtitle
            )
        )
        InfoElementPrimary(
            modifier = DefaultInfoElementModifier,
            title = stringResource(
                com.example.utils.R.string.stat_carbon_dioxide_saved_title,
                dailyState.value.carbonDioxideSaved
            ),
            subTitle = stringResource(
                id = com.example.utils.R.string.stat_carbon_dioxide_saved_subtitle
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewStatisticScreen() {
    StepsTheme {
        StatisticScreen(
            screenTitle = "Отчет",
            dailyState = MutableStateFlow(DailyCounterState()).collectAsState(),
            summaryState = MutableStateFlow(SummaryStatsState()).collectAsState(),
            weekState = MutableStateFlow(BarChartWeek()).collectAsState()
        )
    }
}
