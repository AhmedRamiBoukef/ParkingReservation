package com.example.parkingreservation

import ProfilePage
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.example.parkingreservation.screens.Destination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.navigation.navArgument
import com.example.parkingreservation.screens.Home
import com.example.parkingreservation.screens.LandingPage
import com.example.parkingreservation.screens.Login
import com.example.parkingreservation.screens.MakeReservation
import com.example.parkingreservation.screens.MyHistory
import com.example.parkingreservation.screens.ParkingDetailsScreen
import com.example.parkingreservation.screens.ReservationDetails
import com.example.parkingreservation.screens.ParkingMap
import com.example.parkingreservation.screens.SignUp
import com.example.parkingreservation.viewmodel.CancelReservationModel
import com.example.parkingreservation.viewmodel.GetReservationsModel
import com.example.parkingreservation.viewmodel.LoginModel
import com.example.parkingreservation.viewmodel.ReservationModel
import com.example.parkingreservation.viewmodel.SignupModel
import com.example.parkingreservation.viewmodel.TokenModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GetMain(
    navController: NavHostController,
    tokenModel: TokenModel,
    signupModel: SignupModel,
    loginModel: LoginModel,
    applicationContext: Context,
    currentLocation: Pair<Double, Double>?
) {
    val token = tokenModel.token.value


    NavHost(
        navController = navController,
       // startDestination = Destination.Ho.route
        startDestination = if (token.isNullOrEmpty()) Destination.Landing.route else Destination.Home.route
    ) {

        composable(Destination.Landing.route) { LandingPage(navController,loginModel,tokenModel) }
        composable(Destination.Login.route) { Login(navController,loginModel,tokenModel) }
        composable(Destination.Signup.route) { SignUp(navController,signupModel,tokenModel) }
        composable(Destination.Profile.route) { ProfilePage(tokenModel) }
        composable(Destination.Map.route) { ParkingMap(navController = navController,currentLocation,tokenModel) }

        composable("${Destination.Reservation.route}/{parkingId}",
                arguments = listOf(navArgument("parkingId") { type = NavType.IntType })

        ) {backStackEntry ->
            val parkingId = backStackEntry.arguments?.getInt("parkingId")
            parkingId?.let {
                MakeReservation(
                    navController,
                    applicationContext,
                    parkingId,
                    tokenModel
                )
            }
        }

        composable(Destination.ReservationHistory.route) {
            MyHistory(
                navController,
                applicationContext,
                tokenModel
            )
        }
        composable(
            "${Destination.ReservationDetails.route}/{reservationId}",
            arguments = listOf(navArgument("reservationId") { type = NavType.IntType })
        ) { backStackEntry ->
            val reservationId = backStackEntry.arguments?.getInt("reservationId")
            reservationId?.let {
                ReservationDetails(
                    navController,
                    applicationContext,
                    reservationId,
                    tokenModel
                )
            }
        }
        composable(Destination.Home.route) { Home(navController = navController,currentLocation,tokenModel)}
        composable(
            "${Destination.ParkingDetails.route}/{parkingId}?travelTime={travelTime}",
            arguments = listOf(navArgument("parkingId") { type = NavType.IntType })
        ) { backStackEntry ->
            val parkingId = backStackEntry.arguments?.getInt("parkingId")
            val travelTime = backStackEntry.arguments?.getString("travelTime")
            parkingId?.let {

                travelTime?.let {
                    ParkingDetailsScreen(
                        parkingId,
                        navController,
                        currentLocation,
                        travelTime,
                        tokenModel
                    )
                }
            }
        }



    }
}
