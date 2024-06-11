package com.example.parkingreservation.repository

import com.example.parkingreservation.dao.CreateReservation
import com.example.parkingreservation.data.entities.ReservationRequest

class ReservationRepository(private val createReservation: CreateReservation) {

    suspend fun createReservation(parkingId: Int, nbrHours: Int, dateAndTimeDebut: String) =
        createReservation.createReservation(
            ReservationRequest(parkingId = parkingId, nbrHours = nbrHours, dateAndTimeDebut = dateAndTimeDebut)
        )
    suspend fun getParkingInfo(parkingId: Int) =
        createReservation.getParkingInfo(parkingId)
}