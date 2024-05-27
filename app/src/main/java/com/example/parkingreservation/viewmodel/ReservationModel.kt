package com.example.parkingreservation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parkingreservation.data.entities.ParkingInfoResponse
import com.example.parkingreservation.data.entities.ReservationResponse
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

    suspend fun createReservation(parkingId: Int, nbrHours: Int, dateAndTimeDebut: String): Response<ReservationResponse>? {
        loadingReserve.value = true
        return try {
            val response = withContext(Dispatchers.IO) {
                reservationRepository.createReservation(parkingId, nbrHours, dateAndTimeDebut)
            }

            Log.d("Reservation Model ", "createReservation: ${response.body()}")
            if (response.isSuccessful) {
                val reservationResponse = response.body()
                if (reservationResponse != null) {
                    reservationId.value = reservationResponse.id
                    successReserve.value = true
                } else {
                    errorReserve.value = true
                    message.value = "Invalid response body"
                }
            } else {
                errorReserve.value = true
                val errorMessage = response.body()?.error
                message.value = errorMessage ?: "An error occurred in the reservation"
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