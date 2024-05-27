package com.example.parkingreservation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.parkingreservation.screens.BottomNavBar
import com.example.parkingreservation.screens.Destination
import com.example.parkingreservation.ui.theme.ParkingReservationTheme
import com.example.parkingreservation.viewmodel.CancelReservationModel
import com.example.parkingreservation.viewmodel.GetReservationsModel
import com.example.parkingreservation.viewmodel.LoginModel
import com.example.parkingreservation.viewmodel.ReservationModel
import com.example.parkingreservation.viewmodel.SignupModel
import com.example.parkingreservation.viewmodel.TokenModel

class MainActivity : ComponentActivity() {

    private val signupModel: SignupModel by viewModels {
        SignupModel.Factory((application as MyApplication).signupRepository)
    }
    private val loginModel: LoginModel by viewModels {
        LoginModel.Factory((application as MyApplication).loginRepository)
    }
    private val reservationModel:ReservationModel by viewModels {
        ReservationModel.Factory((application as MyApplication).reservationRepository)
    }
    private val getReservationsModel : GetReservationsModel by viewModels {
        GetReservationsModel.Factory((application as MyApplication).getReservationsRespository)
    }

    private val cancelReservationModel : CancelReservationModel by viewModels {
        CancelReservationModel.Factory((application as MyApplication).cancelReservationRespository)
    }



    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkingReservationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val currentBackStackEntry = navController.currentBackStackEntryAsState().value
                val currentDestination = currentBackStackEntry?.destination

                val showBottomBar = currentDestination?.route in listOf(
                    Destination.Home.route,
                    Destination.Notifications.route,
                    Destination.ReservationHistory.route,
                    Destination.Profile.route
                )
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavBar(navController = navController)
                        }
                    }
                ) {

                    val sharedPreferences = applicationContext.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
                    val tokenModel: TokenModel by viewModels {
                        TokenModel.Factory(sharedPreferences)
                    }
                    GetMain(navController,tokenModel,signupModel,loginModel,reservationModel,getReservationsModel,cancelReservationModel,applicationContext)

                }
            }
        }
    }

}
