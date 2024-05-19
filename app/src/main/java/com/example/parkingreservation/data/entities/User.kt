package com.example.parkingreservation.data.entities

data class SignupRequest(
    val fullName:String,
    val email:String,
    val password:String,
    val phone:String,
    val address:String
)

data class SignupResponse(val success: Boolean)

data class LoginRequest(
    val email:String,
    val password:String
)

data class LoginResponse(val success: Boolean)