package com.example.parkingreservation.dao

import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.LoginRequest
import com.example.parkingreservation.data.entities.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface Login {
    @POST("api/auth/login/")
    suspend fun login(
        @Body requestBody: LoginRequest?
    ): Response<LoginResponse>
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