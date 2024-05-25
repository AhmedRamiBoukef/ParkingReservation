package com.example.parkingreservation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parkingreservation.data.entities.GetReservationResponse
import com.example.parkingreservation.repository.GetReservationsRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetReservationsModel (private val getReservationRepository: GetReservationsRespository) : ViewModel() {
    var loadingActive = mutableStateOf(false)
    var successActive = mutableStateOf(false)
    var activereservation = mutableStateOf<List<GetReservationResponse>?>(null)
    suspend fun getActiveReservations(): List<GetReservationResponse?>? {
        loadingActive.value = true
        return try {
            val response = withContext(Dispatchers.IO) {
                getReservationRepository.getActiveReservations()
            }
            Log.d("get Park Model ", "createReservation: ${response.body()}")

            val info = response.body()
            if (info != null) {
                successActive.value = true
            }
            activereservation.value = info
            info
        } catch (e: Exception) {
            null // Return null indicating error
        } finally {
            loadingActive.value = false
        }
    }


    class Factory(private val getReservationRepository: GetReservationsRespository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GetReservationsModel(getReservationRepository) as T
        }
    }
}