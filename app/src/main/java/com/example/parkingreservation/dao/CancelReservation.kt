package com.example.parkingreservation.dao

import AuthInterceptor
import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.CancelReservationResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface CancelReservation {
    @PUT("api/reservation/{id}")
    suspend fun cancelReservation(@Path("id") id: Int): Response<CancelReservationResponse>


    companion object {
        private var cancelReservationInstance: CancelReservation? = null

        fun getInstance(token: String): CancelReservation {
            if (cancelReservationInstance == null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token))
                    .build()

                cancelReservationInstance = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CancelReservation::class.java)
            }
            return cancelReservationInstance!!
        }
    }
}