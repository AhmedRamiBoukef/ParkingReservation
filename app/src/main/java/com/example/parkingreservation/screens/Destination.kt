package com.example.parkingreservation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val route:String, val icon: ImageVector = Icons.Default.Home, val title: String = "") {
    object Landing:Destination("Landing")

    object Login:Destination("Login")
    object Signup:Destination("Signup")
    object Home : Destination("home", Icons.Default.Home, "Home")
    object Notifications : Destination("notifications", Icons.Default.Notifications, "Notification")
    object Profile : Destination("profile", Icons.Default.Person, "Profile")

    object Reservation:Destination("Reservation")

    object MyActiveReservation:Destination("ActiveReservation")

    object ReservationDetails:Destination("ReservationDetails")

    object ReservationHistory:Destination("ReservationHistory" ,Icons.Default.DateRange,"My Reservations")

}