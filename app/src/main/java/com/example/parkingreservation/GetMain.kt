package com.example.parkingreservation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.parkingreservation.screens.Destination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parkingreservation.screens.Home
import com.example.parkingreservation.screens.LandingPage
import com.example.parkingreservation.screens.Login
import com.example.parkingreservation.screens.ParkingDetailsScreen
import com.example.parkingreservation.screens.SignUp
import com.example.parkingreservation.viewmodel.LoginModel
import com.example.parkingreservation.viewmodel.SignupModel


@Composable
fun GetMain(
    navController: NavHostController,
    signupModel: SignupModel,
    loginModel: LoginModel,
    applicationContext: Context
) {
    NavHost(navController = navController, startDestination = Destination.Home.route ) {
        composable(Destination.Landing.route) { LandingPage(navController) }
        composable(Destination.Login.route) { Login(navController,loginModel,applicationContext) }
        composable(Destination.Signup.route) { SignUp(navController,signupModel,applicationContext) }
        composable(Destination.Home.route) { Home(navController = navController)}
        composable(Destination.ParkingDetails.route) { backStackEntry ->
            val parkingId = backStackEntry.arguments?.getInt("parkingId")
            ParkingDetailsScreen(parkingId)
        }
    }
}
