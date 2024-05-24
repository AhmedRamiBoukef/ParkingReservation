package com.example.parkingreservation.dao

import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.LoginRequest
import com.example.parkingreservation.data.entities.LoginResponse
import com.example.parkingreservation.data.entities.ReservationRequest
import com.example.parkingreservation.data.entities.ReservationResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST



interface CreateReservation {

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOjEsImVtYWlsIjoidGVzdEBlc2kuZHoiLCJpYXQiOjE3MTY1MTE1ODksImV4cCI6MTcxOTEwMzU4OX0.m111eb3QnTny8BR_q5qlwHHhmk9h6BGzM3aPoisYOv8", "Content-Type: application/json")

    @POST("api/reservation/")
    suspend fun createReservation(
        @Body requestBody: ReservationRequest
    ): Response<ReservationResponse>

    companion object {
        private var createReservationInstance: CreateReservation? = null

        fun getInstance(): CreateReservation {
            if (createReservationInstance == null) {
                createReservationInstance = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CreateReservation::class.java)
            }
            return createReservationInstance!!
        }
    }
}