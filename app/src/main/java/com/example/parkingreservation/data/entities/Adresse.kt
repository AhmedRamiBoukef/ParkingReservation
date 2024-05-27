package com.example.parkingreservation.data.entities

data class Address(
    val id: Int,
    val longitude: Double,
    val latitude: Double,
    val wilaya: String,
    val commune: String,
    val street: String
)
