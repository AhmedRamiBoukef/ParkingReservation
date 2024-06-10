package com.example.parkingreservation.dao

import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.GetReservationResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GetReservations {
    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOjEsImVtYWlsIjoidGVzdEBlc2kuZHoiLCJpYXQiOjE3MTY1MTA2MTcsImV4cCI6MTcxOTEwMjYxN30.0YIx0iaClj2cJzYzLm9GlMUDpSuFJNgY0XVYZw8eqr0",
        "Content-Type: application/json"
    )
    @GET("api/reservation/{id}")
    suspend fun getReservationById(@Path("id") id: Int): Response<GetReservationResponse>



    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOjEsImVtYWlsIjoidGVzdEBlc2kuZHoiLCJpYXQiOjE3MTY1MTA2MTcsImV4cCI6MTcxOTEwMjYxN30.0YIx0iaClj2cJzYzLm9GlMUDpSuFJNgY0XVYZw8eqr0",
        "Content-Type: application/json"
    )
    @GET("api/reservation/{status}")
    suspend fun getActiveReservations(@Path("status") status: String): Response<List<GetReservationResponse>>

    companion object {
        private var getReservationsInstance: GetReservations? = null

        fun getInstance(): GetReservations {
            if (getReservationsInstance == null) {
                getReservationsInstance = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GetReservations::class.java)
            }
            return getReservationsInstance!!
        }
    }
}