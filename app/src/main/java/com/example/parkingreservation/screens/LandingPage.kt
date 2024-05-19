package com.example.parkingreservation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.navigation.NavHostController
import com.example.parkingreservation.R

@Composable
fun LandingPage(navController: NavHostController) {
    Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxSize().background(
        Color(0xFFEAEAF3)
    )){
        Image(
            painter = painterResource(id = R.drawable.landing1),
            contentDescription = "landing1",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth(1.0F)
                .padding(horizontal = 40.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Welcome", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF2D2D2D))
        Text(text = "Find a best possible way to park", fontSize = 14.sp, color = Color(0xF02D2D2D))
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            colors = ButtonDefaults.buttonColors(Color(0xFF2D2D2D)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 50.dp, vertical = 10.dp)
                .fillMaxWidth(1.0f),
            onClick = { navController.navigate(Destination.Login.route) }) {
            Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.message),
                    contentDescription = "mail",
                    modifier = Modifier.width(20.dp).height(20.dp)
                )
                Text(
                    text = "Login with email",
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 6.dp).padding(start = 20.dp)
                )
            }
        }
        Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 50.dp, vertical = 10.dp)
                .fillMaxWidth(1.0f),
            onClick = { /*TODO*/ }) {
            Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "mail",
                    modifier = Modifier.width(20.dp).height(20.dp)
                )
                Text(
                    text = "Login with Google",
                    color = Color(0xFF2D2D2D),
                    modifier = Modifier.padding(vertical = 6.dp).padding(start = 20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(text = "Don't have an account?  ", fontSize = 12.sp, color = Color(0xFF2D2D2D))
            Text(text = "Sign Up", fontSize = 12.sp, color = Color(0xFFF43939), modifier = Modifier.clickable {
                navController.navigate(Destination.Signup.route)
            })
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}