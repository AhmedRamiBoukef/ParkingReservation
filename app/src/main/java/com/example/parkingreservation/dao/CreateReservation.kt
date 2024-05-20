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

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOjQsImVtYWlsIjoidGVzdEBlc2kuZHoiLCJpYXQiOjE3MTU0MTk5ODAsImV4cCI6MTcxODAxMTk4MH0.P4xAL6D9ALA7IGruystSoQCYW4U3IIv60nUYbx1DHcI", "Content-Type: application/json")

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