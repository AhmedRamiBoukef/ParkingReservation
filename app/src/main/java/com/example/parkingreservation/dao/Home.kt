package com.example.parkingreservation.dao

import AuthInterceptor
import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.Parking
import com.example.parkingreservation.data.entities.ParkingDetails
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Home {
    @GET("api/parkings/all")
    suspend fun getAllParkings(): Response<List<Parking>>

    @GET("api/parkings/nearest")
    suspend fun getNearestParkings(@Query("longitude") longitude: Double, @Query("latitude") latitude: Double): Response<List<Parking>>

    @GET("api/parkings/popular")
    suspend fun getPopularParkings(): Response<List<Parking>>

    @GET("api/parkings/wanted")
    suspend fun getWantedParkings(): Response<List<Parking>>

    @GET("api/parkings/{id}")
    suspend fun getParkingById(@Path("id") id:Int, @Query("longitude") longitude: Double, @Query("latitude") latitude: Double): Response<ParkingDetails>


    companion object {
        private var home: Home? = null
        fun createHome(token: String): Home {
            if (home == null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token))
                    .build()

                home = Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Home::class.java)
            }
            return home!!
        }
    }
}
