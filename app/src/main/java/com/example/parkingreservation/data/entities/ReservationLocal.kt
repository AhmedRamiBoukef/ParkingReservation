package com.example.parkingreservation.data.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class ReservationLocal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val reservationId : Int,
    val parkingName: String?,
    val parkingAddress: String,
    val pricePerHour: Float?,
    val nbrHours: Int,
    val status: String,
    val dateAndTimeDebut: String,
    val nom: String?,
    val wilaya: String?,
    val commune: String?,
    val street: String?,
    val place:String?,
    val qrcode : String,
    val bokkingId : String,
)
