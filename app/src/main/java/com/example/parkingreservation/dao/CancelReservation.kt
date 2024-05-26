package com.example.parkingreservation.dao

import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.CancelReservationResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface CancelReservation {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOjEsImVtYWlsIjoidGVzdEBlc2kuZHoiLCJpYXQiOjE3MTY1MTA2MTcsImV4cCI6MTcxOTEwMjYxN30.0YIx0iaClj2cJzYzLm9GlMUDpSuFJNgY0XVYZw8eqr0", "Content-Type: application/json")
    @PUT("api/reservation/{id}")
    suspend fun cancelReservation(@Path("id") id: Int): Response<CancelReservationResponse>


    companion object {
        private var cancelReservationInstance: CancelReservation? = null

        fun getInstance(): CancelReservation {
            if (cancelReservationInstance == null) {
                cancelReservationInstance = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CancelReservation::class.java)
            }
            return cancelReservationInstance!!
        }
    }
}