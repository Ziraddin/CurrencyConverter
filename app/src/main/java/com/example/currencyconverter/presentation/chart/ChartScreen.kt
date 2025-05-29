package com.example.currencyconverter.presentation.chart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.presentation.chart.components.AppBar
import com.example.currencyconverter.presentation.chart.components.Chart
import com.example.currencyconverter.presentation.chart.components.CurrencyPairSelector
import com.example.currencyconverter.presentation.chart.components.TimePeriod
import com.example.currencyconverter.presentation.core.theme.Deselected
import com.example.currencyconverter.presentation.core.theme.Selected
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChartScreen(
    state: ChartState,
    onIntent: (ChartIntent) -> Unit,
    viewModel: ChartViewModel,
) {

    LaunchedEffect(Unit) {
        onIntent(ChartIntent.LoadCurrencies)
        onIntent(ChartIntent.GetHistoricalRates)
        viewModel.error.collect { error ->
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        AppBar(text = "Chart")
        CurrencyPairSelector(
            currencyFrom = state.currencyFrom,
            currencyTo = state.currencyTo,
            currencyList = state.currencyList,
            onCurrencySelected = { currency, isFrom ->
                onIntent(ChartIntent.SelectCurrency(currency, isFrom))
            })
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    text = "${state.currencyFrom} to ${state.currencyTo} conversion\nchart",
                )

                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    text = "1 ${state.currencyFrom} = ${state.conversionRate} ${state.currencyTo}",
                )

                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Deselected
                    ),
                    text = "Time Period",
                )

                TimePeriod(
                    selected = state.timePeriod,
                    onSelected = {
                        onIntent(ChartIntent.SetTimePeriod(it))
                        onIntent(ChartIntent.GetHistoricalRates)
                    },
                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                if (!state.isLoading) {
                    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM")
                    val labels = state.historicalRates.map {
                        val parsedDate = LocalDate.parse(it.first, inputFormatter)
                        parsedDate.format(outputFormatter)
                    }
                    val rates = state.historicalRates.map { it.second }
                    val minY = rates.minOrNull() ?: 0.0
                    val maxY = rates.maxOrNull() ?: 1.0

                    Chart(state, rates, labels, minY, maxY)
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(horizontal = 22.dp),
                        color = Selected, strokeWidth = 4.dp,
                    )
                }

                Spacer(
                    modifier = Modifier.height(32.dp)
                )
            }
        }
    }
}

