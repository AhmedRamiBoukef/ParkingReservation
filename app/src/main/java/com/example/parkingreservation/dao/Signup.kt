package com.example.parkingreservation.dao

import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.SignupResponse
import com.example.parkingreservation.data.entities.SignupRequest
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface Signup {
    @POST("api/auth/register/")
    suspend fun signup(
        @Body requestBody:SignupRequest?
    ): Response<SignupResponse>
    companion object {
        var signup: Signup? = null
        fun createSignup(): Signup {
            if(signup ==null) {

                signup = Retrofit.Builder().baseUrl(URL). addConverterFactory(GsonConverterFactory.create()).build(). create(Signup::class.java)
            }
            return signup!!
        }
    }
}