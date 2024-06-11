package com.example.parkingreservation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parkingreservation.data.entities.Parking
import com.example.parkingreservation.data.entities.ParkingDetails
import com.example.parkingreservation.repository.DirectionRepository
import com.example.parkingreservation.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import kotlin.math.log

class HomeViewModel(private val homeRepository: HomeRepository, private val directionRepository: DirectionRepository): ViewModel() {
    var parkings = mutableStateOf<List<Parking>>(emptyList())
    var loading = mutableStateOf(true)
    var error = mutableStateOf(false)
    var parkingDetails = mutableStateOf<ParkingDetails?>(null)
    var travelTimes = mutableStateOf<Map<Int, String>>(emptyMap())




    init {
        fetchNearestParkings(36.7063409, 3.1706161)

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
                val response = withContext(Dispatchers.IO) {
                    homeRepository.getNearestParkings(longitude, latitude)
                }
                if (response.isSuccessful) {
                    val fetchedParkings = response.body() ?: emptyList()
                    Log.d("HomeViewModel", "Fetched ${fetchedParkings.size} parkings")
                    parkings.value = fetchedParkings
                    // Fetch travel time for each parking only after fetching the parkings
                    updateTravelTimes(longitude, latitude, fetchedParkings)
                } else {
                    error.value = true
                    Log.e("HomeViewModel", "Failed to fetch nearest parkings: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching nearest parkings", e)
                error.value = true
            } finally {
                loading.value = false
            }
        }
    }

    private suspend fun updateTravelTimes(longitude: Double, latitude: Double, fetchedParkings: List<Parking>) {
        val map = mutableMapOf<Int, String>()
        val semaphore = Semaphore(10) // Limite à 5 appels simultanés
        Log.d("HomeViewModel", "Starting to fetch travel times for ${fetchedParkings.size} parkings")
        fetchedParkings.forEach { parking ->
            try {
                semaphore.withPermit {
                    val travelTime = withContext(Dispatchers.IO) {
                        Log.d("HomeViewModel", "destination: ${parking.address.latitude},${parking.address.longitude}")
                        Log.d("HomeViewModel", "origin: $latitude,$longitude")
                        directionRepository.getTravelTime(
                            "$longitude,$latitude",
                            "${parking.address.longitude},${parking.address.latitude}"
                        )
                    }
                    Log.d("HomeViewModel", "Fetched travel time for parking ${parking.id}: $travelTime")
                    map[parking.id] = travelTime
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching travel time for parking ${parking.id}", e)
                map[parking.id] = "N/A"
            }
        }
        travelTimes.value = map
        Log.d("HomeViewModel", "Completed fetching travel times")
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



    class Factory(private val homeRepository: HomeRepository,
                  private val directionRepository: DirectionRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(homeRepository, directionRepository) as T
        }
    }
}

