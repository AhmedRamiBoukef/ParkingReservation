package com.example.parkingreservation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter


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
fun ReservationDetails()
{
    var showDialog by remember { mutableStateOf(false) }

    Column (modifier = Modifier.background(Color(0xFFF4F4FA)),){
        Text(text = "27 April 2021, 16:50-18:50",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp)
            ,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
            )
        Row (modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(horizontal = 20.dp, vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Bokking Id : ",
                fontSize = 16.sp,
                color = Color.Black
                )
            Text(text = "1236555555555555",
                fontSize = 16.sp,
                color = Color.Black)

        }

        Text(text = "Reservation Information",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)

            )
            Column (verticalArrangement = Arrangement.spacedBy(3.dp)){
                InfoElement(key = "Name Parking : " , value ="The Name " )
                InfoElement(key = "Address : " , value ="58 street oued Smar  " )
                InfoElement(key = "Place : " , value ="A-6 " )
            }

            Spacer(modifier = Modifier.height(25.dp))
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

            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Total Price",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(text = "150.00$",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)
            }

            Column(modifier = Modifier.padding(top = 15.dp)
                .background(Color.White)
                .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            )
            {
                val imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8cdrhPJkLrA8ozuv2TjOoWZOloyUL8rGaf0Y0X13UVQ&s"
                val painter = rememberAsyncImagePainter(model = imageUrl)

                Image(
                    painter = painter,
                    contentDescription = "reservation image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(0.7f),
                    contentScale = ContentScale.Fit
                )
                Button( onClick = { showDialog = true},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000)) ,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(top = 25.dp)
                        .fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Cancel Reservation",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black) ,
                        modifier = Modifier
                            .fillMaxWidth(0.45f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black) ,
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