package com.example.parkingreservation.data.entities

data class Notification(
    val id: Int,
    val seen: Boolean,
    val dateAndTimeNotification: String,
    val title: String,
    val description: String
)
