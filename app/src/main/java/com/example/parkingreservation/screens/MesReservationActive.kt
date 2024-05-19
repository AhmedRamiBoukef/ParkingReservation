package com.example.parkingreservation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkingreservation.Components.ReservationElement

@Composable
fun MesReservationActive() // we have active , finished  , expired , canceled
{
    val textList = List(15) { index -> "Text${index + 1}" }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 30.dp)
        .background(Color(0xFFF4F4FA)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(text = "Mes Reservations Active",
            modifier = Modifier.padding(top = 10.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
            )
        LazyColumn(modifier = Modifier.padding(top = 10.dp)){
            items(textList) { text ->
                ReservationElement()
            }
        }
    }
}