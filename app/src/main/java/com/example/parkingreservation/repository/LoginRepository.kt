package com.example.parkingreservation.repository

import com.example.parkingreservation.dao.Login
import com.example.parkingreservation.data.entities.LoginRequest

class LoginRepository(private val login: Login) {
    suspend fun login(email:String, password:String) = login.login(
        LoginRequest(email=email,password=password)
    )
}