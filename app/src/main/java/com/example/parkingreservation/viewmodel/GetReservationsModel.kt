package com.example.parkingreservation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parkingreservation.data.entities.Address
import com.example.parkingreservation.data.entities.GetReservationResponse
import com.example.parkingreservation.data.entities.Parking
import com.example.parkingreservation.database.AppDatabase
import com.example.parkingreservation.repository.GetReservationsRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetReservationsModel (private val getReservationRepository: GetReservationsRespository) : ViewModel() {

    var loadingActive = mutableStateOf(false)
    var successActive = mutableStateOf(false)
    var activereservation = mutableStateOf<List<GetReservationResponse>?>(null)


    var loading = mutableStateOf(false)
    var success= mutableStateOf(false)
    var reservation = mutableStateOf<GetReservationResponse?>(null)
    suspend fun getReservations(status : String,db:AppDatabase): List<GetReservationResponse?>? {

        loadingActive.value = true

        return try {
            Log.d("INFOOOOO1", "getReservations:jjjjjjjjjjjjjjjjjjjj")

            val response = withContext(Dispatchers.IO) {
                getReservationRepository.getActiveReservations(status = status)
            }
            Log.d("INFOOOOO12", "getReservations:jjjjjjjjjjjjjjjjjjjj")


            var info = response.body()
            if (info != null) {
                successActive.value = true

            }

            else{
                Log.d("INFOOOOO", "getReservations: ${info}")

                successActive.value = true


            }
            activereservation.value = info
            info
        } catch (e: Exception) {
            null // Return null indicating error
        } finally {
            if(activereservation.value==null)
            {
                val localReservations =  db.reservationDao().getAllReservations(status = status)
                val response: List<GetReservationResponse> = localReservations.map { localReservation ->
                    GetReservationResponse(
                        id = localReservation.id,
                        reservationRandomId = localReservation.bokkingId,
                        dateAndTimeReservation = "", // Fill this with appropriate data from ReservationLocal if available
                        nbrHours = localReservation.nbrHours,
                        totalPrice = localReservation.pricePerHour?.toDouble() ?: 0.0, // Convert Float to Double
                        userId = 0, // Fill this with appropriate data from ReservationLocal if available
                        parkingId = 0, // Fill this with appropriate data from ReservationLocal if available
                        dateAndTimeDebut = localReservation.dateAndTimeDebut,
                        position = localReservation.place ?: "", // Fill this with appropriate data from ReservationLocal if available
                        qRcode = localReservation.qrcode,
                        status = localReservation.status,
                        parking = Parking(
                            id = 0, // Fill this with appropriate data from ReservationLocal if available
                            photo = "", // Fill this with appropriate data from ReservationLocal if available
                            nom = localReservation.parkingName ?: "", // Fill this with appropriate data from ReservationLocal if available
                            addressId = 0, // Fill this with appropriate data from ReservationLocal if available
                            description = "", // Fill this with appropriate data from ReservationLocal if available
                            nbrTotalPlaces = 0, // Fill this with appropriate data from ReservationLocal if available
                            pricePerHour = localReservation.pricePerHour?.toDouble() ?: 0.0, // Convert Float to Double
                            address = Address(
                                id = 0, // Fill this with appropriate data from ReservationLocal if available
                                longitude = 0.0, // Fill this with appropriate data from ReservationLocal if available
                                latitude = 0.0, // Fill this with appropriate data from ReservationLocal if available
                                wilaya = localReservation.wilaya ?: "", // Fill this with appropriate data from ReservationLocal if available
                                commune = localReservation.commune ?: "", // Fill this with appropriate data from ReservationLocal if available
                                street = localReservation.street ?: "" // Fill this with appropriate data from ReservationLocal if available
                            )
                        )
                    )
                }
                val info = response
                activereservation.value = info

            }
            loadingActive.value = false

        }
    }




    suspend fun getReservationById(id : Int): GetReservationResponse? {
        loading.value = true
        return try {
            val response = withContext(Dispatchers.IO) {
                getReservationRepository.getReservationById(id=id)
            }
            Log.d("get Park Model ", "createReservation: ${response.body()}")

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




    class Factory(private val getReservationRepository: GetReservationsRespository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GetReservationsModel(getReservationRepository) as T
        }
    }
}