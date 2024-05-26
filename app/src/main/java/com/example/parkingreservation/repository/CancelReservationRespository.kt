package com.example.parkingreservation.repository

import com.example.parkingreservation.dao.CancelReservation

class CancelReservationRespository(private  val cancelReservation: CancelReservation) {
    suspend fun cancelReservation(id:Int) =cancelReservation.cancelReservation(id)

}