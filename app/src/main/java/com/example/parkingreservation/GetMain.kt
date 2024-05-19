package com.example.parkingreservation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.parkingreservation.screens.Destination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parkingreservation.screens.LandingPage
import com.example.parkingreservation.screens.Login
import com.example.parkingreservation.screens.MesReservationActive
import com.example.parkingreservation.screens.MyHistory
import com.example.parkingreservation.screens.Reservation
import com.example.parkingreservation.screens.ReservationDetails
import com.example.parkingreservation.screens.SignUp


@Composable
fun GetMain(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destination.Landing.route ) {
        composable(Destination.Landing.route) { LandingPage() }
        composable(Destination.Login.route) { Login() }
        composable(Destination.Signup.route) { SignUp() }
        composable(Destination.Signup.route) { SignUp() }
        composable(Destination.Reservation.route) { Reservation()}
        composable(Destination.MyActiveReservation.route){ MesReservationActive()}
        composable(Destination.ReservationHistory.route){ MyHistory()}
        composable(Destination.ReservationDetails.route) { ReservationDetails()}

    }
}
