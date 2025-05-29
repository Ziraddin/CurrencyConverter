package com.example.currencyconverter.presentation.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.currencyconverter.presentation.core.theme.Background
import com.example.currencyconverter.presentation.core.theme.Deselected
import com.example.currencyconverter.presentation.core.theme.Selected


@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    val screens = listOf(Screen.Search, Screen.Bookmark)
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = Background
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = if (currentRoute == screen.route) screen.selectedIcon!! else screen.icon!!),
                        contentDescription = stringResource(id = screen.label!!),
                        modifier = modifier
                            .size(24.dp)
                    )
                },
                label = {
                    Text(
                        stringResource(
                            id = screen.label!!
                        )
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Selected,
                    selectedTextColor = Selected,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Deselected,
                    unselectedTextColor = Deselected
                ),
            )
        }
    }
}