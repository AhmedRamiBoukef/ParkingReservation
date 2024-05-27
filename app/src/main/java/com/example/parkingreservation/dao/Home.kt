package com.example.parkingreservation.dao

import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.Parking
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
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

    companion object {
        var home: Home? = null
        fun createHome(): Home {
            if (home == null) {
                home = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Home::class.java)
            }
            return home!!
        }
    }
}
