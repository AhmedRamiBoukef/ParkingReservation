package com.example.parkingreservation.dao

import AuthInterceptor
import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.GetReservationResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GetReservations {
    @Headers(
        "Content-Type: application/json"
    )
    @GET("api/reservation/{id}")
    suspend fun getReservationById(@Path("id") id: Int): Response<GetReservationResponse>



    @Headers(
        "Content-Type: application/json"
    )
    @GET("api/reservation/{status}")
    suspend fun getActiveReservations(@Path("status") status: String): Response<List<GetReservationResponse>>

    companion object {
        private var getReservationsInstance: GetReservations? = null

        fun getInstance(token: String): GetReservations {
            if (getReservationsInstance == null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token))
                    .build()

                getReservationsInstance = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GetReservations::class.java)
            }
            return getReservationsInstance!!
        }
    }
}