package com.example.parkingreservation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.parkingreservation.screens.Destination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parkingreservation.screens.Home
import com.example.parkingreservation.screens.LandingPage
import com.example.parkingreservation.screens.Login
import com.example.parkingreservation.screens.ParkingMap
import com.example.parkingreservation.screens.Profile
import com.example.parkingreservation.screens.SignUp
import com.example.parkingreservation.viewmodel.LoginModel
import com.example.parkingreservation.viewmodel.SignupModel
import com.example.parkingreservation.viewmodel.TokenModel


@Composable
fun GetMain(
    navController: NavHostController,
    tokenModel: TokenModel,
    signupModel: SignupModel,
    loginModel: LoginModel
) {
    val token = tokenModel.token.value

    NavHost(navController = navController, startDestination = if (token.isNullOrEmpty()) Destination.Landing.route else Destination.Home.route) {
        composable(Destination.Landing.route) { LandingPage(navController) }
        composable(Destination.Login.route) { Login(navController,loginModel,tokenModel) }
        composable(Destination.Signup.route) { SignUp(navController,signupModel,tokenModel) }
        composable(Destination.Home.route) { ParkingMap() }
        composable(Destination.Profile.route) { Profile() }
    }
}
