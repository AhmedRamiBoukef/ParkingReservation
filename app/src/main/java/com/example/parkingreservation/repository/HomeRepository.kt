package com.example.parkingreservation.repository

import com.example.parkingreservation.dao.Home

class HomeRepository(private val home: Home) {
    suspend fun getAllParkings() = home.getAllParkings()
    suspend fun getNearestParkings(longitude: Double, latitude: Double) = home.getNearestParkings(longitude, latitude)
    suspend fun getPopularParkings() = home.getPopularParkings()
    suspend fun getWantedParkings() = home.getWantedParkings()
    suspend fun getParkingById(id:Int,longitude: Double,latitude: Double) = home.getParkingById(id, longitude, latitude)
}