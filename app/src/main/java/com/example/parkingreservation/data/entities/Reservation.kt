package com.example.parkingreservation.data.entities

data class ReservationRequest(
    val parkingId:Int,
    val nbrHours : Int,
    val dateAndTimeDebut : String,
)

data class ReservationResponse(
    val id: Int,
    val error : String
)





data class ParkingInfoResponse(
    val parking: Parking,
    val distance: Float? // Assuming distance can be nullable
)






data class GetReservationResponse(
    val id: Int,
    val reservationRandomId: String,
    val dateAndTimeReservation: String,
    val nbrHours: Int,
    val totalPrice: Double,
    val userId: Int,
    val parkingId: Int,
    val dateAndTimeDebut: String,
    val position: String,
    val qRcode: String,
    val status: String,
    val parking: Parking
)

data class CancelReservationResponse(
    val id: Int,
    val error : String
)
