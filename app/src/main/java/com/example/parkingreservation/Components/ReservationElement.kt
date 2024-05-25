package com.example.parkingreservation.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.parkingreservation.R
import com.example.parkingreservation.data.entities.GetReservationResponse
import com.example.parkingreservation.screens.Destination

@Composable
fun ReservationElement(reservation: GetReservationResponse, navController: NavHostController)
{
    Row (modifier = Modifier
        .padding(top=25.dp)
        .fillMaxWidth(0.9f)
        .background(Color.White, shape = RoundedCornerShape(10.dp))
        .padding(15.dp)
        .clickable {  navController.navigate(Destination.ReservationDetails.route) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ){
        Box(modifier = Modifier.background(color = Color.Transparent,shape = RoundedCornerShape(90))){
            val painter: Painter = painterResource(id = R.drawable.park)
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(10)), // Clip the image to a circular shape

                painter = painter,
                contentScale = ContentScale.FillBounds,
                contentDescription = "reservation image",
                // Other modifiers like contentScale, alignment, etc. can be added here
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column (modifier = Modifier.fillMaxWidth(),){
            Row ( modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .background(color = when (reservation.status) {
                            "expired", "canceled" -> Color.Red
                            "finished" -> Color.Blue
                            else -> Color.Green // Change this to the default color if needed
                        },shape = RoundedCornerShape(16.dp))
                        .padding(horizontal = 4.dp),
                ) {
                    Row (horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(2.dp)){
                        Text(
                            text = "${reservation.status}",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold, // Change the font weight to bold
                            modifier = Modifier.padding(vertical = 2.dp , horizontal = 4.dp)
                                .fillMaxWidth()
                        )
                    }

                }

            }
            Text(text = "${reservation.parking.nom}", fontSize = 18.sp , fontWeight = FontWeight.Bold , modifier = Modifier.padding(top = 7.dp))
                Text(text = "${reservation.parking.address.commune} , ${reservation.parking.address.wilaya}",
                    color = Color(0x4B2D2D2D), // Hexadecimal color value (e.g., #3366FF)
                    fontSize = 14.sp,

                    )

            Text(
                modifier = Modifier.padding(top = 15.dp),
                fontSize = 12.sp,
                text = "${reservation.dateAndTimeDebut}")

        }
    }
}