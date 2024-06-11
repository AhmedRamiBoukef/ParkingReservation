package com.example.parkingreservation.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.parkingreservation.Components.ReservationElement
import com.example.parkingreservation.dao.GetReservations
import com.example.parkingreservation.repository.GetReservationsRespository
import com.example.parkingreservation.viewmodel.GetReservationsModel
import com.example.parkingreservation.viewmodel.TokenModel

@Composable
fun MesReservationActive(
    navController: NavHostController,
    applicationContext: Context,
    tokenModel: TokenModel
) // we have active , finished  , expired , canceled
{
    val token: String = tokenModel.getToken()!!;
    val getReservationsRespository = GetReservationsRespository(GetReservations.getInstance(token))
    val getReservationsModel: GetReservationsModel = viewModel(factory = GetReservationsModel.Factory(getReservationsRespository))

    LaunchedEffect(Unit) {
        try {
            val reponse = getReservationsModel.getReservations("active")
            if (getReservationsModel.successActive.value) {
                Log.d("Active Reservations", "MesReservationActive: ${getReservationsModel.activereservation.value}")
            } else {
                Toast.makeText(applicationContext, "Error in loading Page", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    if (getReservationsModel.loadingActive.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F4FA)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFFF43939))
        }
    }else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
                .background(Color(0xFFF4F4FA)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mes Reservations Active",
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            if (getReservationsModel.activereservation.value != null) {
                LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                    items(getReservationsModel.activereservation.value!!) { reservation ->
                        reservation?.let {
                            ReservationElement(reservation = it , navController=navController)
                        }
                    }
                }
            } else {
                Text(text = "No active reservations available.")
            }

        }
    }
}