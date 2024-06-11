package com.example.parkingreservation.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.parkingreservation.URL
import com.example.parkingreservation.dao.GetReservations
import com.example.parkingreservation.repository.CancelReservationRespository
import com.example.parkingreservation.repository.GetReservationsRespository
import com.example.parkingreservation.viewmodel.CancelReservationModel
import com.example.parkingreservation.viewmodel.GetReservationsModel
import com.example.parkingreservation.viewmodel.TokenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal


@Composable
fun InfoElement(key : String , value :String)
{
    Row (modifier = Modifier
        .fillMaxWidth()

        .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween){
        Text(text = "${key}",
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(text = "${value}",
            fontSize = 16.sp,
            color = Color.Black)
    }
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .height(1f.dp)
    ) {
        drawLine(
            color = Color(0xFFB9B9B9),
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 1f,
        )
    }
}

@Composable
fun ReservationDetails(
    navController: NavHostController,
    applicationContext: Context,
    reservationId: Int,
    tokenModel: TokenModel
)
{
    val token: String = tokenModel.getToken()!!;
    val getReservationsRespository = GetReservationsRespository(GetReservations.getInstance(token))
    val getReservationsModel: GetReservationsModel = viewModel(factory = GetReservationsModel.Factory(getReservationsRespository))
    val cancelReservationRespository = CancelReservationRespository(com.example.parkingreservation.dao.CancelReservation.getInstance(token))
    val cancelReservationModel: CancelReservationModel = viewModel(factory = CancelReservationModel.Factory(cancelReservationRespository))

    LaunchedEffect(Unit) {
        try {
            val reponse = getReservationsModel.getReservationById(reservationId)
            if (getReservationsModel.success.value) {
                Log.d("Active Reservations", "MesReservationActive: ${getReservationsModel.reservation.value}")
            } else {
                Toast.makeText(applicationContext, "Error in loading Page", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    var showDialog by remember { mutableStateOf(false) }

    if (getReservationsModel.loading.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F4FA)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFFF43939))
        }
    }else {
        Column(modifier = Modifier.background(Color(0xFFF4F4FA)),) {
            Text(
                text = "${getReservationsModel.reservation.value?.dateAndTimeDebut} (${getReservationsModel.reservation.value?.nbrHours} Hours)",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(horizontal = 20.dp, vertical = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Bokking Id : ",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = "${getReservationsModel.reservation.value?.reservationRandomId}",
                    fontSize = 14.sp,
                    color = Color.Black
                )

            }

            Text(
                text = "Reservation Information",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)

            )
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                InfoElement(key = "Status :", value = "${getReservationsModel.reservation.value?.status}")
                InfoElement(key = "Name Parking : ", value = "${getReservationsModel.reservation.value?.parking?.nom}")
                InfoElement(key = "Address : ", value = "${getReservationsModel.reservation.value?.parking?.address?.commune} , ${getReservationsModel.reservation.value?.parking?.address?.wilaya}")
                InfoElement(key = "Place : ", value = "${getReservationsModel.reservation.value?.position }")
            }

            Spacer(modifier = Modifier.height(25.dp))
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(horizontal = 20.dp)
                    .height(1f.dp)
            ) {
                drawLine(
                    color = Color(0xFFB9B9B9),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1f,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Price",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                val pricePerHour = getReservationsModel.reservation.value?.parking?.pricePerHour?.toBigDecimal() ?: BigDecimal.ZERO
                val nbrHours = getReservationsModel.reservation.value?.nbrHours?.toBigDecimal() ?: BigDecimal.ZERO
                val totalPrice = pricePerHour * nbrHours
                Text(


                text = "$totalPrice$",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .background(Color.White)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                )
            {
                if(getReservationsModel.reservation.value?.status == "active") {
                val imageUrl =
                    "${URL}${getReservationsModel.reservation.value?.qRcode}"
                val painter = rememberAsyncImagePainter(model = imageUrl)

                Image(
                    painter = painter,
                    contentDescription = "reservation image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(0.7f),
                    contentScale = ContentScale.Fit
                )
                    Button(
                        onClick = {
                            showDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000)),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(top = 15.dp)
                            .fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Cancel Reservation",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                else{
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .fillMaxSize(0.7f),
                            horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                        ){
                        Text(text = "${getReservationsModel.reservation.value?.status}",
                            fontSize = 32.sp,

                            color = when (getReservationsModel.reservation.value?.status) {
                                "expired", "canceled" -> Color.Red
                                "finished" -> Color.Blue
                                else -> Color.Green },

                                textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }


                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {

                                CoroutineScope(Dispatchers.Main).launch {
                                    try {
                                        val reponse =
                                            cancelReservationModel.cancelReservationById(reservationId)
                                        if (!cancelReservationModel.success.value) {

                                            Toast.makeText(
                                                applicationContext,
                                                "Error in Cancel Reservation",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        else{
                                            val reponse = getReservationsModel.getReservationById(reservationId)
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            applicationContext,
                                            "An error occurred: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                showDialog = false
                                      },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            modifier = Modifier
                                .fillMaxWidth(0.45f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            modifier = Modifier
                                .fillMaxWidth(0.45f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Cancel")
                        }
                    },
                    title = {
                        Text(text = "Confirm your cancellation")
                    },
                    text = {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxWidth(),
                                text = "Are you sure that  you want to cancel the reservation ? ",
                                fontWeight = FontWeight.SemiBold
                            )


                        }
                    }
                )
            }


        }
    }
}