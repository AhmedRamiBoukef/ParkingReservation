package com.example.parkingreservation

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient



    private val signupModel: SignupModel by viewModels {
        SignupModel.Factory((application as MyApplication).signupRepository)
    }
    private val loginModel: LoginModel by viewModels {
        LoginModel.Factory((application as MyApplication).loginRepository)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            ParkingReservationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val currentBackStackEntry = navController.currentBackStackEntryAsState().value
                val currentDestination = currentBackStackEntry?.destination

                val showBottomBar = currentDestination?.route in listOf(
                    Destination.Landing.route,
                    Destination.Login.route,
                    Destination.Signup.route
                )
                // State to store current location
                val currentLocation = remember { mutableStateOf<Pair<Double, Double>?>(null) }

                // Request location permissions if not already granted
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
                } else {
                    fetchLocation(currentLocation)
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (!showBottomBar) {
                            BottomNavBar(navController = navController)
                        }
                    }
                ) {

                    val sharedPreferences = applicationContext.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
                    val tokenModel: TokenModel by viewModels {
                        TokenModel.Factory(sharedPreferences)
                    }
                    GetMain(navController,tokenModel,signupModel,loginModel,applicationContext,currentLocation.value)


                }
            }
        }

    }
    @SuppressLint("MissingPermission")
    private fun fetchLocation(currentLocation: MutableState<Pair<Double, Double>?>) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                currentLocation.value = Pair(it.latitude, it.longitude)
                Log.d(TAG, "Current Location: ${currentLocation.value}")
            }
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
        private const val TAG = "MainActivity"
    }


}
