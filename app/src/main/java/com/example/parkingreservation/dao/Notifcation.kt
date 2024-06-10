package com.example.parkingreservation.dao

import com.example.parkingreservation.URL
import com.example.parkingreservation.data.entities.Notification
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NotificationApi {
    @GET("api/notifications/")
    suspend fun getNotifications(): Response<List<Notification>>

    companion object {
        var notificationApi: NotificationApi? = null
        fun createNotificationApi(): NotificationApi {
            if (notificationApi == null) {
                notificationApi = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NotificationApi::class.java)
            }
            return notificationApi!!
        }
    }
}
