package com.example.parkingreservation.data.entities

data class ReservationRequest(
    val parkingId:Int,
    val nbrHours : Int,
    val dateAndTimeDebut : String,
)

data class ReservationResponse(
    val id: Int,
    val reservationRandomId: String,
    val dateAndTimeReservation: String,
    val nbrHours: Int,
    val totalPrice: Float,
    val userId: Int,
    val parkingId: Int,
    val dateAndTimeDebut: String,
    val position: String,
    val qRcode: String,
    val status: String,
    val parking: ParkingResponse
)

data class ParkingResponse(
    val id: Int,
    val photo: String,
    val nom: String,
    val addressId: Int,
    val description: String,
    val nbrTotalPlaces: Int,
    val pricePerHour: Float,
    val address: AddressResponse
)

data class AddressResponse(
    val id: Int,
    val longitude: Float,
    val latitude: Float,
    val wilaya: String,
    val commune: String,
    val street: String
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
