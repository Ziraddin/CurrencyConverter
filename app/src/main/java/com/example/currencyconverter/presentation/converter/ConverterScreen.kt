package com.example.currencyconverter.presentation.converter

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.currencyconverter.R
import com.example.currencyconverter.presentation.converter.components.CurrencyCard

@Composable
fun ConverterScreen(
    state: ConverterState, onIntent: (ConverterIntent) -> Unit, viewModel: ConverterViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    var currentError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.error.collect { error ->
            currentError = error
            showDialog = true
            Log.e("Error", error)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Currency Converter",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        if (showDialog) {
            AlertDialog(
                containerColor = Color.White,
                onDismissRequest = {
                    showDialog = false
                },
                shape = RoundedCornerShape(12.dp),
                title = { Text(text = "🤯 Error Occured!", color = Color.Red) },
                text = { Text(text = currentError!!) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                        },
                    ) {
                        Text(
                            text = "Dismiss",
                            color = Color.Blue,
                        )
                    }
                })
        }

        CurrencyCard(
            label = "From",
            amount = state.amountFrom,
            currency = state.currencyFrom,
            currencyList = state.currencyList,
            onAmountChange = { onIntent(ConverterIntent.EnterAmount(it)) },
            onCurrencyChange = { onIntent(ConverterIntent.SelectCurrency(it, true)) },
            isFirst = true
        )

        // Lottie swap animation
        var isPlaying by remember { mutableStateOf(false) }

        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.swap_animation)
        )

        val animationState = animateLottieCompositionAsState(
            composition = composition, isPlaying = isPlaying, iterations = 1, speed = 1.5f
        )

        LaunchedEffect(animationState.isAtEnd && isPlaying) {
            if (animationState.isAtEnd) {
                isPlaying = false
            }
        }

        LottieAnimation(
            composition = composition,
            progress = { animationState.progress },
            modifier = Modifier
                .height(64.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    isPlaying = true
                    onIntent(ConverterIntent.SwapCurrencies)
                })

        CurrencyCard(
            label = "To",
            amount = state.amountTo,
            currency = state.currencyTo,
            currencyList = state.currencyList,
            isFirst = false,
            onAmountChange = { onIntent(ConverterIntent.EnterAmount(it)) },
            onCurrencyChange = { onIntent(ConverterIntent.SelectCurrency(it, false)) })
    }
}
