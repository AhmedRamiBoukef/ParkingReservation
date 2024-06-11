package com.example.parkingreservation.dao

import AuthInterceptor
import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.LoginRequest
import com.example.parkingreservation.data.entities.LoginResponse
import com.example.parkingreservation.data.entities.ParkingInfoResponse
import com.example.parkingreservation.data.entities.ReservationRequest
import com.example.parkingreservation.data.entities.ReservationResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface CreateReservation {

    @POST("api/reservation/")
    suspend fun createReservation(
        @Body requestBody: ReservationRequest
    ): Response<ReservationResponse>






    @Headers(
        "Content-Type: application/json"
    )
    @GET("api/parkings/{id}")
    suspend fun getParkingInfo(@Path("id") id: Int): Response<ParkingInfoResponse>

    companion object {
        private var createReservationInstance: CreateReservation? = null

        fun getInstance(token: String): CreateReservation {
            if (createReservationInstance == null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token))
                    .build()

                createReservationInstance = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CreateReservation::class.java)
            }
            return createReservationInstance!!
        }
    }
}