package com.example.currencyconverter.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.currencyconverter.R

sealed class Screen(
    val route: String,
    @StringRes val label: Int? = null,
    @DrawableRes val icon: Int? = null,
    @DrawableRes val selectedIcon: Int? = null,
) {
    data object Search : Screen(
        route = "home",
        label = R.string.home,
        icon = R.drawable.ic_home,
        selectedIcon = R.drawable.ic_home_filled
    )

    data object Bookmark : Screen(
        route = "chart",
        label = R.string.chart,
        icon = R.drawable.ic_chart,
        selectedIcon = R.drawable.ic_chart_filled
    )
}