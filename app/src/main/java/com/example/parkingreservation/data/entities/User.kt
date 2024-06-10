package com.example.parkingreservation.data.entities

data class SignupRequest(
    val fullName:String,
    val email:String,
    val password:String,
    val phone:String,
    val address:String
)

data class SignupResponse(
    val token: String
)

data class LoginRequest(
    val email:String,
    val password:String
)

data class LoginResponse(
    val token: String
)

data class FCMTokenRequest(
    val fcmToken: String
)
data class LoginGoogleRequest(
    val email:String,
    val fullName:String,
    val googleId:String
)

data class LoginGoogleResponse(
    val token: String
)