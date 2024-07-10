package com.example.designsystem

import android.graphics.Paint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Paddings
import com.example.designsystem.theme.StepsTheme
import com.example.utils.model.BarChartDay
import com.example.utils.model.BarChartWeek
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

@Composable
fun DSBarChart(
    modifier: Modifier = Modifier,
    weekState: State<BarChartWeek>,
    parentWidth: Int,
    animated: Boolean = false
) {
    val graphHeight = 300
    val barData: BarChartData = createBarData(weekState.value.week)

    BarChart(
        modifier = Modifier
            .padding(
                start = Paddings.medium,
                top = Paddings.large,
                end = Paddings.medium,
                bottom = Paddings.medium
            )
            .then(modifier),
        barData = barData,
        animated = animated,
        width = parentWidth,
        height = graphHeight,
        barArrangement = Arrangement.SpaceEvenly
    )
}

@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    barData: BarChartData,
    animated: Boolean = false,
    width: Int,
    height: Int,
    barArrangement: Arrangement.Horizontal
) {

    val xAxisScaleHeight = (height * 0.1).dp
    val yAxisTextWidth = (height * 0.1).dp

    // Форма Вертикальной полоски
    val barShape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)


    val textStyle = MaterialTheme.typography.bodyMedium
    val textColor = MaterialTheme.colorScheme.primary


    val lineHeightXAxis = 10.dp

    Box(
        modifier = Modifier
            .width(width.dp)
            .then(modifier),
        contentAlignment = Alignment.BottomStart
    ) {

        BarChartHorizontalLines(
            topPadding = xAxisScaleHeight,
            height = height.dp,
            barData = barData,
            textColor = textColor
        )

        Box(
            modifier = Modifier
                .padding(start = 50.dp)
                .width(width.dp - yAxisTextWidth)
                .height(height.dp + xAxisScaleHeight),
            contentAlignment = BottomCenter
        ) {

            Row(
                modifier = Modifier
                    .width(width.dp - yAxisTextWidth),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = barArrangement
            ) {

                barData.data.forEach { it: BarData ->

                    var animationTriggered by remember {
                        mutableStateOf(false)
                    }
                    val graphBarHeight = if (animated) {
                        LaunchedEffect(key1 = true) {
                            animationTriggered = true
                        }

                        animateFloatAsState(
                            targetValue = if (animationTriggered) it.height else 0f,
                            animationSpec = tween(
                                durationMillis = 4000,
                                easing = LinearEasing
                            ),
                            label = ""
                        )
                    } else {
                        mutableFloatStateOf(it.height)
                    }

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(bottom = lineHeightXAxis)
                                .clip(barShape)
                                .width(20.dp)
                                .height(height.dp - lineHeightXAxis)
                                .background(Color.Transparent),
                            contentAlignment = BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(barShape)
                                    .fillMaxWidth()
                                    .fillMaxHeight(graphBarHeight.value)
                                    .background(it.color)
                            )
                        }


                        // Подпись для каждого дня по оси X
                        Box(
                            modifier = Modifier
                                .height(xAxisScaleHeight)
                                .padding(bottom = Paddings.small),
                            contentAlignment = Center
                        ) {
                            Text(
                                text = it.label,
                                style = textStyle,
                                color = textColor
                            )

                        }

                    }

                }

            }

            // Толщина горизонтальной линии поверх оси Х для разделения диаграммы и их значений
            val horizontalLineHeight = 5.dp
            // Горизонтальной линии поверх оси Х для разделения диаграммы и их значений
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                horizontalAlignment = CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .padding(bottom = xAxisScaleHeight + 3.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .fillMaxWidth()
                        .height(horizontalLineHeight)
                        .background(Color.Gray)
                )

            }


        }


    }

}

/**
 * Рисует значения и пунктирныи линии на оси y
 */
@Composable
private fun BarChartHorizontalLines(
    topPadding: Dp,
    height: Dp,
    barData: BarChartData,
    textColor: Color
) {
    val density = LocalDensity.current
    val yTextSize = MaterialTheme.typography.bodyMedium.fontSize

    val textPaint = remember(density) {
        Paint().apply {
            color = textColor.hashCode()
            textAlign = Paint.Align.CENTER
            textSize = density.run { yTextSize.toPx() }
        }
    }

    // Эффект для пунктирной линии
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    // Координаты для создания пунктирных линий по оси Y
    val yCoordinates = mutableListOf<Float>()

    Column(
        modifier = Modifier
            .padding(top = topPadding)
            .height(height)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = CenterHorizontally
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            val yAxisScaleSpacing = 100f
            // Текст вначале каждой пунктирной линии по оси Y
            val yAxisScaleText = (barData.maxStepsCount) / 10
            (0..10).forEach { i ->
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        (yAxisScaleText * i).toString(),
                        30f,
                        size.height - yAxisScaleSpacing - i * size.height / 10f,
                        textPaint
                    )
                }
                yCoordinates.add(size.height - yAxisScaleSpacing - i * size.height / 10f)
            }

            // Прорисовка горизонтальных пунктирных линий
            (1..10).forEach {
                drawLine(
                    start = Offset(x = yAxisScaleSpacing + 30f, y = yCoordinates[it]),
                    end = Offset(x = size.width, y = yCoordinates[it]),
                    color = textColor,
                    strokeWidth = 5f,
                    pathEffect = pathEffect
                )
            }

        }

    }
}

@Composable
private fun createBarData(week: List<BarChartDay>): BarChartData {

    val list = arrayListOf<BarData>()
    val maxStepsValue = week.maxOf { it.steps }
    week.forEach { day ->
        list.add(
            BarData(
                height = (day.steps / maxStepsValue.toFloat()),
                color = if ((day.steps / day.dailyGoal) >= 1) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.tertiary
                },
                label = day.date.dayOfMonth.toString(),
            )
        )
    }
    return BarChartData(
        data = list,
        minStepCount = week.minOf { it.steps },
        maxStepsCount = maxStepsValue
    )
}

data class BarData(
    //от 0 .. 1
    val height: Float,
    val color: Color,
    val label: String
)

data class BarChartData(
    val data: List<BarData>,
    val minStepCount: Int,
    val maxStepsCount: Int,
)

@Preview(showBackground = true)
@Composable
fun PreviewStatisticScreen() {
    StepsTheme {
        Column {
            DSBarChart(
                weekState = MutableStateFlow(
                    BarChartWeek(
                        week = listOf(
                            BarChartDay(
                                LocalDate.now().minusDays(6),
                                steps = 6000
                            ),
                            BarChartDay(
                                LocalDate.now().minusDays(5),
                                steps = 8000
                            ),
                            BarChartDay(
                                LocalDate.now().minusDays(4),
                                steps = 1000
                            ),
                            BarChartDay(
                                LocalDate.now().minusDays(3),
                                steps = 2000
                            ),
                            BarChartDay(
                                LocalDate.now().minusDays(2),
                                steps = 200
                            ),
                            BarChartDay(
                                LocalDate.now().minusDays(1),
                                steps = 3500
                            ),
                            BarChartDay(
                                LocalDate.now(),
                                steps = 7600
                            )
                        ),
                        dailyGoal = 7000
                    )
                ).collectAsState(),
                parentWidth = 400,
            )
        }

    }
}