package com.example.parkingreservation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parkingreservation.data.entities.Parking
import com.example.parkingreservation.data.entities.ParkingDetails
import com.example.parkingreservation.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val homeRepository: HomeRepository): ViewModel() {
    var parkings = mutableStateOf<List<Parking>>(emptyList())
    var loading = mutableStateOf(true)
    var error = mutableStateOf(false)
    var parkingDetails = mutableStateOf<ParkingDetails?>(null)

    init {
        fetchNearestParkings(35.55, 6.5)
    }

    fun fetchAllParkings() {
        viewModelScope.launch {
            loading.value = true
            error.value = false
            try {
                val response = withContext(Dispatchers.IO) { homeRepository.getAllParkings() }
                if (response.isSuccessful) {
                    parkings.value = response.body() ?: emptyList()
                } else {
                    error.value = true
                }
            } catch (e: Exception) {
                error.value = true
            } finally {
                loading.value = false
            }
        }
    }

    fun fetchNearestParkings(longitude: Double, latitude: Double) {
        viewModelScope.launch {
            loading.value = true
            error.value = false
            try {
                val response = withContext(Dispatchers.IO) { homeRepository.getNearestParkings(longitude, latitude) }
                if (response.isSuccessful) {
                    parkings.value = response.body() ?: emptyList()
                } else {
                    error.value = true
                }
            } catch (e: Exception) {
                error.value = true
            } finally {
                loading.value = false
            }
        }
    }

    fun fetchPopularParkings() {
        viewModelScope.launch {
            loading.value = true
            error.value = false
            try {
                val response = withContext(Dispatchers.IO) { homeRepository.getPopularParkings() }
                if (response.isSuccessful) {
                    parkings.value = response.body() ?: emptyList()
                } else {
                    error.value = true
                }
            } catch (e: Exception) {
                error.value = true
            } finally {
                loading.value = false
            }
        }
    }

    fun fetchWantedParkings() {
        viewModelScope.launch {
            loading.value = true
            error.value = false
            try {
                val response =
                    withContext(Dispatchers.IO) { homeRepository.getWantedParkings() }
                if (response.isSuccessful) {
                    parkings.value = response.body() ?: emptyList()
                } else {
                    error.value = true
                }
            } catch (e: Exception) {
                error.value = true
            } finally {
                loading.value = false
            }
        }
    }

    fun fetchParkingById(id:Int,longitude: Double,latitude: Double){
        viewModelScope.launch {
            loading.value = true
            error.value = false
            try {
                val response =
                    withContext(Dispatchers.IO) { homeRepository.getParkingById(id, longitude, latitude) }
                if (response.isSuccessful) {
                    parkingDetails.value = response.body()
                } else {
                    error.value = true
                }
            } catch (e: Exception) {
                error.value = true
            } finally {
                loading.value = false
            }
        }

    }

    class Factory(private val homeRepository: HomeRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(homeRepository) as T
        }
    }
}
