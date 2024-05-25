package com.example.parkingreservation.screens

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        Destination.Home,
        Destination.Notifications,
        Destination.MyReservations,
        Destination.Profile
    )
    BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                modifier = Modifier.background(Color(0xFFF0F3F5)),
                icon = { Icon(screen.icon, contentDescription = null, tint = Color.Black) },
                selected = false,
                onClick = {
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