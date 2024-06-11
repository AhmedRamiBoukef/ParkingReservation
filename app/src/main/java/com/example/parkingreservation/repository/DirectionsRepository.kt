package com.example.parkingreservation.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class DirectionRepository(private val apiKey: String) {

    suspend fun getTravelTime(origin: String, destination: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://maps.googleapis.com/maps/api/directions/json?origin=$origin&destination=$destination&key=$apiKey")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    Log.d("DirectionRepository", "API Response: $response")

                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    if (status == "OK") {
                        val routes = jsonResponse.getJSONArray("routes")
                        if (routes.length() > 0) {
                            val legs = routes.getJSONObject(0).getJSONArray("legs")
                            if (legs.length() > 0) {
                                val duration = legs.getJSONObject(0).getJSONObject("duration").getString("text")
                                return@withContext duration
                            }
                        }
                        Log.e("DirectionRepository", "No legs found in the response")
                        "N/A"
                    } else {
                        Log.e("DirectionRepository", "API returned status: $status")
                        "N/A"
                    }
                } else {
                    Log.e("DirectionRepository", "Failed to fetch directions: $responseCode")
                    "N/A"
                }
            } catch (e: Exception) {
                Log.e("DirectionRepository", "Error fetching travel time", e)
                "N/A"
            }
        }
    }
}