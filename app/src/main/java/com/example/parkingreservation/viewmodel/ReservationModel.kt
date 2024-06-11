package com.example.parkingreservation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parkingreservation.data.entities.ParkingInfoResponse
import com.example.parkingreservation.data.entities.ReservationLocal
import com.example.parkingreservation.data.entities.ReservationResponse
import com.example.parkingreservation.database.AppDatabase
import com.example.parkingreservation.repository.ReservationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.math.log

class ReservationModel(private val reservationRepository: ReservationRepository) : ViewModel() {
    var loadingReserve = mutableStateOf(false)
    var loading = mutableStateOf(false)

    var errorReserve = mutableStateOf(false)
    var error = mutableStateOf(false)

    var successReserve = mutableStateOf(false)
    var success = mutableStateOf(false)

    var message = mutableStateOf<String?>("")
    var reservationId = mutableStateOf<Int?>(null) // Property to hold the reservation ID
    var parkingInfo = mutableStateOf<ParkingInfoResponse?>(null) // Property to hold parking information
    suspend fun createReservation(parkingId: Int, nbrHours: Int, dateAndTimeDebut: String,db : AppDatabase): Response<ReservationResponse>? {
        loadingReserve.value = true
        return try {
            val response = withContext(Dispatchers.IO) {
                reservationRepository.createReservation(parkingId, nbrHours, dateAndTimeDebut)
            }

           

            if (response.isSuccessful) {
                val reservationResponse = response.body()
                if (reservationResponse != null) {
                    reservationId.value = reservationResponse.id
                    db.reservationDao().insert(ReservationLocal(place = reservationResponse.position,parkingName =reservationResponse.parking.nom , parkingAddress = "${reservationResponse.parking.address.commune },${reservationResponse.parking.address.wilaya }", pricePerHour = reservationResponse.parking.pricePerHour, commune = reservationResponse.parking.address.commune, nbrHours = reservationResponse.nbrHours, status = reservationResponse.status, dateAndTimeDebut = reservationResponse.dateAndTimeDebut, nom = reservationResponse.parking.nom, wilaya = reservationResponse.parking.address.wilaya, street = reservationResponse.parking.address.street , qrcode = reservationResponse.qRcode, bokkingId = reservationResponse.reservationRandomId, reservationId = reservationResponse.id))

                    successReserve.value = true
                } else {
                    errorReserve.value = true
                    message.value = "Invalid response body"
                }
            } else {
                errorReserve.value = true
            }
            response // Return the response
        } catch (e: Exception) {
            errorReserve.value = true
            message.value = "An exception occurred: ${e.message}"
            null
        } finally {
            loadingReserve.value = false
        }
    }

    suspend fun getParkingInfo(parkingId: Int): ParkingInfoResponse? {
        loading.value = true
        return try {
            val response = withContext(Dispatchers.IO) {
                reservationRepository.getParkingInfo(parkingId)
            }
            Log.d("get Park Model ", "createReservation: ${response.body()}")

            val info = response.body()
            if (info != null) {
                success.value = true
            } else {
                error.value = true
            }
            parkingInfo.value = info
            info
        } catch (e: Exception) {
            error.value = true
            null // Return null indicating error
        } finally {
            loading.value = false
        }
    }

    class Factory(private val reservationRepository: ReservationRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReservationModel(reservationRepository) as T
        }
    }
}