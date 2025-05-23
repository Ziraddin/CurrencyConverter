package com.example.currencyconverter.presentation.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.currencyconverter.presentation.converter.ConverterIntent
import com.example.currencyconverter.presentation.converter.ConverterScreen
import com.example.currencyconverter.presentation.converter.ConverterViewModel
import com.example.currencyconverter.presentation.core.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ConverterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onIntent(ConverterIntent.LoadCurrencies)
        viewModel.onIntent(ConverterIntent.GetStatus)
        setContent {
            CurrencyConverterTheme {
                val state = viewModel.state.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ConverterScreen(
                        state = state.value,
                        onIntent = viewModel::onIntent,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
