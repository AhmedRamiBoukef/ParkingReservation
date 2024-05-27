package com.example.parkingreservation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parkingreservation.data.entities.CancelReservationResponse
import com.example.parkingreservation.data.entities.GetReservationResponse
import com.example.parkingreservation.repository.CancelReservationRespository
import com.example.parkingreservation.repository.GetReservationsRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CancelReservationModel (private val cancelReservationRespository: CancelReservationRespository) : ViewModel() {
    var loading = mutableStateOf(false)
    var success= mutableStateOf(false)
    var reservation = mutableStateOf<CancelReservationResponse?>(null)


    suspend fun cancelReservationById(id : Int): CancelReservationResponse? {
        loading.value = true
        return try {
            val response = withContext(Dispatchers.IO) {
                cancelReservationRespository.cancelReservation(id=id)
            }
            Log.d("Cancel Reservation  ", "cancelReservation: ${response.body()}")

            val info = response.body()
            if (info != null) {
                success.value = true
            }
            reservation.value = info
            info
        } catch (e: Exception) {
            null // Return null indicating error
        } finally {
            loading.value = false
        }
    }


    class Factory(private val cancelReservationRespository: CancelReservationRespository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CancelReservationModel(cancelReservationRespository) as T
        }
    }

}