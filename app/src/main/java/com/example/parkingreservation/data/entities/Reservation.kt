package com.example.parkingreservation.data.entities

data class ReservationRequest(
    val parkingId:Int,
    val nbrHours : Int,
    val dateAndTimeDebut : String,
)

data class ReservationResponse(
    val id: Int
)