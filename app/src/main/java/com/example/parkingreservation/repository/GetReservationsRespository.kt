package com.example.parkingreservation.repository

import com.example.parkingreservation.dao.GetReservations

class GetReservationsRespository(private  val getReservations: GetReservations) {
    suspend fun getActiveReservations() = getReservations.getActiveReservations()
}

