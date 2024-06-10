package com.example.parkingreservation.screens


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.parkingreservation.R
import com.example.parkingreservation.viewmodel.LoginModel
import com.example.parkingreservation.viewmodel.TokenModel
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun Login(navController: NavHostController, loginModel: LoginModel, tokenModel: TokenModel) {
    var email = remember { mutableStateOf(TextFieldValue("")) }
    var password = remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisibility = remember { mutableStateOf(false) }
    val applicationContext = LocalContext.current


    Column (
        modifier = Modifier.background(Color(0xFF130F26)),
    ) {
        Box(modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.bg_landing),
                contentDescription = "landing",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
            Text(text = "Glad to see you!! \uD83D\uDC4B\uD83C\uDFFB",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
                ,modifier = Modifier.padding(top = 50.dp, start = 30.dp),
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxHeight()
            .background(
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                color = Color(0xFFF4F4FA)
            )) {
            Column(modifier = Modifier.padding(horizontal = 30.dp, vertical = 30.dp)) {
                OutlinedTextField(
                    shape = RoundedCornerShape(15.dp),
                    value = email.value,
                    onValueChange = { email.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Email", color = Color(0x9E2D2D2D)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    textStyle = TextStyle(color = Color(0x9E2D2D2D)),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    shape = RoundedCornerShape(15.dp),
                    value = password.value,
                    onValueChange = { password.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Password", color = Color(0x9E2D2D2D)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    textStyle = TextStyle(color = Color(0x9E2D2D2D)),
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),


                )
            }
            Column (modifier = Modifier.padding(bottom = 40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    colors = ButtonDefaults.buttonColors(Color(0xFF130F26)),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 10.dp)
                        .fillMaxWidth(1.0f),
                    onClick = {
                        loginModel.login(email.value.text, password.value.text) { token ->
                            if (token != null) {
                                tokenModel.saveToken(token)
                                if (tokenModel.getFCMToken() == null){
                                    CoroutineScope(Dispatchers.IO).launch {
                                        val token = Firebase.messaging.token.await()
                                        Log.d("djamel token", "fcm Token : ${token}")
                                        loginModel.sendFCMToken(token)
                                        withContext(Dispatchers.Main) {
                                            tokenModel.saveFCMToken(token)
                                        }
                                    }
                                }
                                navController.navigate(Destination.Home.route) {
                                    popUpTo(Destination.Landing.route) { inclusive = true }
                                }
                                Toast.makeText(applicationContext, "You are signed in!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .padding(start = 20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text(text = "Don't have an account?", fontSize = 12.sp, color = Color(0xFF2D2D2D))
                    Text(text = "  Login", fontSize = 12.sp, color = Color(0xFFF43939), modifier = Modifier.clickable {
                        navController.navigate(Destination.Login.route)
                    })
                }
            }
        }
    }
}