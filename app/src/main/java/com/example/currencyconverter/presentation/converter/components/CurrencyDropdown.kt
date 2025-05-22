package com.example.currencyconverter.presentation.converter.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.domain.model.Currency

@Composable
fun CurrencyDropdown(
    selectedCurrency: String,
    currencyList: List<Currency>,
    onCurrencySelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(0.dp, Color.White),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFF0F0F0))
        ) {
            Text(selectedCurrency, color = Color(0xFF000000))
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color(0xFF000000))
        }


        DropdownMenu(
            offset = DpOffset(8.dp, 0.dp),
            containerColor = Color(0xFFF0F0F0),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.2f)
                .padding(horizontal = 6.dp),
            shape = RoundedCornerShape(12.dp),
        ) {
            currencyList.forEachIndexed { index, currency ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${currency.code} (${currency.name})",
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                color = Color(0xFF000000)
                            )
                            Text(
                                text = currency.symbolNative,
                                color = Color(0xFF000000)
                            )
                        }
                    },
                    onClick = {
                        onCurrencySelected(currency.code)
                        expanded = false
                    },
                )
                if (index < currencyList.lastIndex) {
                    HorizontalDivider(
                        color = Color.Gray,
                        thickness = 0.5.dp,
                    )
                }
            }
        }
    }
}
