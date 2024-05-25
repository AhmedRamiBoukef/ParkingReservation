package com.example.parkingreservation

import android.content.Context
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
import com.example.parkingreservation.viewmodel.GetReservationsModel
import com.example.parkingreservation.viewmodel.LoginModel
import com.example.parkingreservation.viewmodel.ReservationModel
import com.example.parkingreservation.viewmodel.SignupModel


@Composable
fun GetMain(
    navController: NavHostController,
    signupModel: SignupModel,
    loginModel: LoginModel,
    reservationModel: ReservationModel,
    getReservationsModel: GetReservationsModel,
    applicationContext: Context
) {
    NavHost(navController = navController, startDestination = Destination.ReservationHistory.route ) {

        composable(Destination.Landing.route) { LandingPage(navController) }
        composable(Destination.Login.route) { Login(navController,loginModel,applicationContext) }
        composable(Destination.Signup.route) { SignUp(navController,signupModel,applicationContext)}
        composable(Destination.Reservation.route) { Reservation(navController,reservationModel,applicationContext, parkingId = 7)}
        composable(Destination.MyActiveReservation.route){ MesReservationActive(navController,getReservationsModel,applicationContext)}
        composable(Destination.ReservationHistory.route){ MyHistory(navController,getReservationsModel,applicationContext)}
        composable(Destination.ReservationDetails.route) { ReservationDetails(navController,getReservationsModel,applicationContext,reservationId = 28 )}


    }
}
