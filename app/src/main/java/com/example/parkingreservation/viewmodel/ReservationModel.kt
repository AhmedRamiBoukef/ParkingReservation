package com.example.parkingreservation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parkingreservation.repository.ReservationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservationModel(private val reservationRepository: ReservationRepository) : ViewModel() {
    var loading = mutableStateOf(false)
    var error = mutableStateOf(false)
    var success = mutableStateOf(false)

    fun createReservation(parkingId: Int, nbrHours: Int, dateAndTimeDebut: String) {
        loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = reservationRepository.createReservation(parkingId, nbrHours, dateAndTimeDebut)
            if (response.isSuccessful) {
                success.value = true
            } else {
                error.value = true
            }
            loading.value = false
        }
    }

    class Factory(private val reservationRepository: ReservationRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReservationModel(reservationRepository) as T
        }
    }
}