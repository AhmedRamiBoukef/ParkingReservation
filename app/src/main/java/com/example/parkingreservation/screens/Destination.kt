package com.example.parkingreservation.screens

sealed class Destination(val route:String) {
    object Landing:Destination("Landing")
    object Login:Destination("Login")
    object Signup:Destination("Signup")
    object Home:Destination("Home")
    object ParkingDetails:Destination("ParkingDetails/{parkingId}"){
        fun createRoute(parkingId : Int)= "ParkingDetails/$parkingId"
    }
    object Notification:Destination("Notification")
}