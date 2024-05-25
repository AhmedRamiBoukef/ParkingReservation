package com.example.parkingreservation.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.parkingreservation.Components.ReservationElement
import com.example.parkingreservation.viewmodel.GetReservationsModel


@Composable
fun ActiveStatus(status: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable(onClick = onClick)

            .background(
                Color(0xFF000000),
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp,
                    bottomStart = 25.dp,
                    bottomEnd = 25.dp
                )
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = status,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Canvas(modifier = Modifier.size(8.dp)
            ) {
                drawCircle(Color.Red)
            }
        }
    }
}


@Composable
fun InactiveStatus(status: String, onClick: () -> Unit) {
    Text(
        text = status,
        color = Color.Gray,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.clickable(onClick = onClick)
    )
}


@Composable
fun MyHistory(
    navController: NavHostController,
    getReservationsModel: GetReservationsModel,
    applicationContext: Context
) // we have active , finished  , expired , canceled
{
    val textList = List(15) { index -> "Text${index + 1}" }
    var activeStatus by remember { mutableStateOf("Active") }
    val statuses = listOf("Active", "Expired", "Finished", "Cancelled")

    LaunchedEffect(activeStatus) {
        try {
                when (activeStatus) {
                    "Active" -> getReservationsModel.getReservations("active")
                    "Expired" -> getReservationsModel.getReservations("expire")
                    "Finished" -> getReservationsModel.getReservations("finish")
                    "Cancelled" -> getReservationsModel.getReservations("cancel")
                }
            if (getReservationsModel.successActive.value) {
                Log.d("Active Reservations", "MesReservationActive: ${getReservationsModel.activereservation.value}")
            } else {
                Toast.makeText(applicationContext, "Error in loading Page", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
                .background(Color(0xFFF4F4FA)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "My Reservations History",
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxWidth()


            ) {
                statuses.forEach { status ->
                    if (status == activeStatus) {
                        ActiveStatus(status) { activeStatus = status }
                    } else {
                        InactiveStatus(status) { activeStatus = status  }
                    }
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
                if (getReservationsModel.activereservation.value != null) {
                    LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                        items(getReservationsModel.activereservation.value!!) { reservation ->
                            reservation?.let {
                                ReservationElement(reservation = it, navController = navController)
                            }
                        }
                    }
                } else {
                    Text(text = "No active reservations available.")
                }
                if (getReservationsModel.activereservation.value?.isEmpty() == true) {
                    Text(
                        modifier = Modifier.padding(top = 200.dp),
                        text = "No ${activeStatus} Reservation"
                    )
                }
            }
        }


}