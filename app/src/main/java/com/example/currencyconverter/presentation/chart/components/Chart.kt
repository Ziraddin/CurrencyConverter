package com.example.currencyconverter.presentation.chart.components

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.presentation.chart.ChartState
import com.example.currencyconverter.presentation.core.theme.Selected
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun Chart(
    state: ChartState,
    rates: List<Double>,
    labels: List<String>,
    minY: Double,
    maxY: Double,
) {
    val screenWith = LocalConfiguration.current.screenWidthDp
    val chartWidth =
        if (labels.size > 5) (labels.size * screenWith / 5).dp else (screenWith / 1.1).dp

    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        LineChart(
            modifier = Modifier
                .width(chartWidth)
                .heightIn(min = 200.dp, max = 300.dp)
                .padding(horizontal = 22.dp),
            data = listOf(
                Line(
                    label = "${state.currencyFrom} to ${state.currencyTo} Rate",
                    values = rates,
                    color = SolidColor(Selected),
                    firstGradientFillColor = Selected.copy(alpha = .5f),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(700, easing = EaseInOutCubic),
                    gradientAnimationDelay = 200,
                    dotProperties = DotProperties(
                        enabled = true,
                        radius = 5.dp,
                        strokeColor = SolidColor(Selected),
                    )
                )
            ),
            labelProperties = LabelProperties(
                enabled = true,
                labels = labels,
                textStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
            ),
            minValue = minY,
            maxValue = maxY,
            animationMode = AnimationMode.Together { it * 200L },
        )
    }
}