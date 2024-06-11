package com.example.parkingreservation.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.ReservationLocal
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Dao
interface LocalReservationDao {
    @Insert
    suspend fun insert(reservation: ReservationLocal)

    @Query("SELECT * FROM reservations WHERE status=:status")
    suspend fun getAllReservations(status : String): List<ReservationLocal>


}