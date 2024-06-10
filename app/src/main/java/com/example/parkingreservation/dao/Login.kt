package com.example.parkingreservation.dao

import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.FCMTokenRequest
import com.example.parkingreservation.data.entities.LoginRequest
import com.example.parkingreservation.data.entities.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface Login {
    @POST("api/auth/login/")
    suspend fun login(
        @Body requestBody: LoginRequest?
    ): Response<LoginResponse>

    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOjEsImVtYWlsIjoidGVzdEBlc2kuZHoiLCJpYXQiOjE3MTY1MTA2MTcsImV4cCI6MTcxOTEwMjYxN30.0YIx0iaClj2cJzYzLm9GlMUDpSuFJNgY0XVYZw8eqr0",
        "Content-Type: application/json"
    )
    @PUT("api/reservation/addfcm")
    suspend fun sendFCMToken(@Body fcmToken:FCMTokenRequest?)
    companion object {
        var login: Login? = null
        fun createLogin(): Login {
            if(login ==null) {
                login = Retrofit.Builder().baseUrl(URL). addConverterFactory(GsonConverterFactory.create()).build(). create(Login::class.java)
            }
            return login!!
        }
    }
}