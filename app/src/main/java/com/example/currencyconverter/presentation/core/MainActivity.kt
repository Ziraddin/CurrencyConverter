package com.example.currencyconverter.presentation.core

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.presentation.chart.ChartViewModel
import com.example.currencyconverter.presentation.converter.ConverterViewModel
import com.example.currencyconverter.presentation.core.theme.Background
import com.example.currencyconverter.presentation.core.theme.CurrencyConverterTheme
import com.example.currencyconverter.presentation.navigation.AppNavHost
import com.example.currencyconverter.presentation.navigation.BottomNavigationBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModelConverter: ConverterViewModel by viewModels()
    private val viewModelChart: ChartViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Background,
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            modifier = Modifier
                        )
                    },
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        viewModelConverter = viewModelConverter,
                        viewModelChart = viewModelChart,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}
