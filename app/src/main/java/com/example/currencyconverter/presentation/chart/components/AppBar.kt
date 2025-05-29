package com.example.currencyconverter.presentation.chart.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppBar(text: String) {
    Text(
        modifier = Modifier.padding(
            top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp
        ), text = text, style = MaterialTheme.typography.headlineSmall
    )

    HorizontalDivider()
}