package com.example.parkingreservation.screens

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        Destination.Home,
        Destination.Map,
        Destination.ReservationHistory,
        Destination.Profile
    )
    var selectedItem by remember { mutableStateOf(items[0].route) }

    BottomNavigation {
        items.forEach { screen ->
            val isSelected = screen.route == selectedItem
            BottomNavigationItem(
                modifier = Modifier.background(Color(0xFFF0F3F5)),
                icon = { Icon(screen.icon, contentDescription = null, tint = if (isSelected) Color.Red else Color.Black) },
                selected = false,
                onClick = {
                    selectedItem = screen.route
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}