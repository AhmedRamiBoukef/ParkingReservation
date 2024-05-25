package com.example.parkingreservation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.parkingreservation.ui.theme.ParkingReservationTheme
import com.example.parkingreservation.viewmodel.GetReservationsModel
import com.example.parkingreservation.viewmodel.LoginModel
import com.example.parkingreservation.viewmodel.ReservationModel
import com.example.parkingreservation.viewmodel.SignupModel

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkingReservationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    GetMain(navController,signupModel,loginModel,reservationModel,getReservationsModel,applicationContext)
                }
            }
        }
    }

}
