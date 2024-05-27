package com.example.parkingreservation.data.entities

data class Parking(
    val id: Int,
    val photo: String,
    val nom: String,
    val addressId: Int,
    val description: String,
    val nbrTotalPlaces: Int,
    val pricePerHour: Double,
    val address: Address
)
