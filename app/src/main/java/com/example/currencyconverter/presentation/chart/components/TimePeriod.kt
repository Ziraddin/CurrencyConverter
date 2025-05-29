package com.example.currencyconverter.presentation.chart.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.presentation.core.theme.Background
import com.example.currencyconverter.presentation.core.theme.Selected

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimePeriod(
    selected: String, onSelected: (String) -> Unit
) {
    val options = listOf("48H", "3D", "5D", "1W", "2W")

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.spacedBy(
            12.dp, alignment = Alignment.CenterHorizontally
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        options.forEach { label ->
            val navyColor = Color(0xFF001F54) // Navy

            OutlinedButton(
                onClick = { onSelected(label) },
                shape = RoundedCornerShape(50),
                border = BorderStroke(
                    1.dp, if (label == selected) navyColor else Color.Gray
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = if (label == selected) Background else Color.Gray,
                    containerColor = if (label == selected) Selected else Color.Transparent
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = label)
            }

        }
    }
}