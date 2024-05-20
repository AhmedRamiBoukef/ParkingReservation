package com.example.parkingreservation.data.entities

data class Parking(
    val id: Int,
    val photo: String,
    val nom: String,
    val address: String,
    val description: String,
    val nbrTotalPlaces: Int,
    val nbrDisponiblePlaces: Int,
    val places: String
)

