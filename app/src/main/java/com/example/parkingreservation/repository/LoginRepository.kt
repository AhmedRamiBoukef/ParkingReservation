package com.example.parkingreservation.repository

import com.example.parkingreservation.dao.Login
import com.example.parkingreservation.data.entities.LoginGoogleRequest
import com.example.parkingreservation.data.entities.LoginRequest

class LoginRepository(private val login: Login) {
    suspend fun login(email:String, password:String) = login.login(
        LoginRequest(email=email,password=password)
    )
    suspend fun loginGoogle(email:String, fullName:String, googleId:String) = login.loginWithGoogle(
        LoginGoogleRequest(email=email,fullName=fullName,googleId=googleId)
    )
}