package com.example.currencyconverter.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.currencyconverter.presentation.chart.ChartScreen
import com.example.currencyconverter.presentation.chart.ChartViewModel
import com.example.currencyconverter.presentation.converter.ConverterScreen
import com.example.currencyconverter.presentation.converter.ConverterViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModelConverter: ConverterViewModel,
    viewModelChart: ChartViewModel
) {
    val stateConverter = viewModelConverter.state.collectAsState()
    val stateChart = viewModelChart.state.collectAsState()

    NavHost(
        navController = navController, modifier = modifier, startDestination = Screen.Search.route
    ) {
        composable(Screen.Search.route) {
            ConverterScreen(
                state = stateConverter.value,
                onIntent = viewModelConverter::onIntent,
                viewModel = viewModelConverter,
            )
        }
        composable(Screen.Bookmark.route) {
            ChartScreen(
                state = stateChart.value,
                onIntent = viewModelChart::onIntent,
                viewModel = viewModelChart
            )
        }
    }
}