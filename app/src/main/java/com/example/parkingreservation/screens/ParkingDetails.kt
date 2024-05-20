package com.example.parkingreservation.screens

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkingreservation.R

@Composable
fun ParkingDetailsScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0XFFF4F4FA))
            .padding(horizontal = 20.dp)
            .padding(vertical = 40.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(Color.Transparent),

                ) {
                Text(
                    text = "<",
                    fontSize = 30.sp,
                    color = Color(0XFF2D2D2D).copy(alpha = 0.5f),
                    modifier = Modifier
                        .background(Color(0XFFEAEAF3), shape = RoundedCornerShape(5.dp))
                        .size(40.dp)
                        .padding(start = 12.dp)



                )
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Details",
                    color = Color(0XFF2D2D2D),
                    modifier = Modifier.padding(top = 10.dp)
                )
                Spacer(modifier = Modifier.width(80.dp))
            }

        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.parking_details_image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            Icon(painter = painterResource(id = R.drawable.parking_details_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 20.dp)
                    .background(color = Color(0XFFF43939), shape = RoundedCornerShape(30.dp))
                    .padding(10.dp),
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Graha Mall",
            color = Color(0xFF2D2D2D),
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Text(
            text = "123 Dhaka Street",
            color = Color(0xFF2D2D2D).copy(alpha = 0.6f),
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        Row {
            Row(
                modifier = Modifier.background(color = Color(0XFFFFF3F3), shape = RoundedCornerShape(10.dp))
            ) {
                Icon(painter = painterResource(id = R.drawable.position_icon), contentDescription = null, tint = Color(0XFFF43939))
                Text(
                    text = "500 m away",
                    color = Color(0xFFF43939).copy(alpha = 0.8f),
                    modifier = Modifier
                        .background(
                            color = Color(0xFFFFF3F3),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)

                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                modifier = Modifier.background(color = Color(0XFFFFF3F3), shape = RoundedCornerShape(10.dp))
            ) {
                Icon(painter = painterResource(id = R.drawable.time_circle_icon), contentDescription =null, tint = Color(0XFFF43939) )
                Text(
                    text = "7 min",
                    color = Color(0xFFF43939).copy(alpha = 0.8f),
                    modifier = Modifier
                        .background(
                            color = Color(0xFFFFF3F3),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)

                )
            }

        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Information",
            color = Color(0xFF2D2D2D),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start),
            fontSize = 25.sp
        )
        Text(
            text = "24/7 parking facility with cctv camera, professional security guard, chair disble, floor parking list facilities. You will get hassle parking facilities with 35% discount on first parking...",
            color = Color(0xFF2D2D2D).copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier.background(color = Color(0XFF130F26), shape = RoundedCornerShape(10.dp))
        ) {
            Text(text = "Book Now")

        }
    }
}