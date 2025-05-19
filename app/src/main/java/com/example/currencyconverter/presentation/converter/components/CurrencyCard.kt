package com.example.currencyconverter.presentation.converter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyCard(
    label: String,
    amount: String,
    currency: String,
    currencyList: List<String>,
    onAmountChange: (String) -> Unit,
    onCurrencyChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(8.dp))

            AmountTextField(
                value = amount,
                onValueChange = onAmountChange
            )

            Spacer(modifier = Modifier.height(8.dp))

            CurrencyDropdown(
                selectedCurrency = currency,
                currencyList = currencyList,
                onCurrencySelected = onCurrencyChange
            )
        }
    }
}
