package com.example.parkingreservation.repository

import com.example.parkingreservation.dao.GetReservations

class GetReservationsRespository(private  val getReservations: GetReservations) {
    suspend fun getActiveReservations(status: String) = getReservations.getActiveReservations(status)
    suspend fun getReservationById(id:Int)= getReservations.getReservationById(id = id)


}

