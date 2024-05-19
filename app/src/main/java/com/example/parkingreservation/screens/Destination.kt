package com.example.parkingreservation.screens

sealed class Destination(val route:String) {
    object Landing:Destination("Landing")
    object Login:Destination("Login")
    object Signup:Destination("Signup")

    object Reservation:Destination("Reservation")

    object MyActiveReservation:Destination("ActiveReservation")

    object ReservationDetails:Destination("ReservationDetails")

    object ReservationHistory:Destination("ReservationHistory")

}