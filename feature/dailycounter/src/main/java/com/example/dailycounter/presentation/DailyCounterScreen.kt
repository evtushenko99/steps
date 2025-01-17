package com.example.dailycounter.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.DSAppBar
import com.example.designsystem.theme.Paddings
import com.example.designsystem.theme.StepsTheme
import com.example.utils.R
import com.example.utils.model.DailyCounterState
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.DecimalFormat

/**
 * Экран расчета ежедневной активности
 *
 * @author Евтушенко Максим 17.05.2024
 */
@Composable
internal fun DailyCounterScreen(
    screenTitle: String,
    state: State<DailyCounterState>
) {
    val format = DecimalFormat.getIntegerInstance()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {
            DSAppBar(screenTitle)
        },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(Paddings.medium)
                    .verticalScroll(rememberScrollState())
            ) {
                Row {
                    Text(
                        text = format.format(state.value.stepsTaken),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = stringResource(id = R.string.stat_steps_taken),
                        modifier = Modifier
                            .padding(Paddings.xssmall),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                LinearProgressIndicator(
                    progress = { state.value.stepsTaken / state.value.dailyGoal.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Paddings.small),
                )
                Text(
                    text = "${stringResource(id = R.string.setting_daily_goal_title)} ${state.value.dailyGoal}",
                    modifier = Modifier.padding(top = Paddings.small),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium
                )


                val treeResource =
                    getTreeResource(state.value.stepsTaken.toDouble() / state.value.dailyGoal)

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    Image(
                        painter = painterResource(id = treeResource),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 48.dp)
                            .fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    )
}

private fun getTreeResource(progress: Double) =
    when {
        progress < .2 -> R.drawable.stage_1
        progress < .4 -> R.drawable.stage_2
        progress < .6 -> R.drawable.stage_3
        progress < .8 -> R.drawable.stage_4
        progress < 1 -> R.drawable.stage_5
        else -> R.drawable.stage_6
    }

@Preview(showBackground = true)
@Composable
fun PreviewDailyCounterScreen() {
    StepsTheme {
        DailyCounterScreen(
            screenTitle = "Сегодня",
            MutableStateFlow(DailyCounterState()).collectAsState()
        )

    }
}
