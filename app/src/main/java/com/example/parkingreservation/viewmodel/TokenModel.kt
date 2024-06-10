package com.example.parkingreservation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TokenModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    val token = mutableStateOf<String?>(null)

    init {
        viewModelScope.launch {
            token.value = getToken()
        }
    }

    private fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun saveToken(newToken: String) {
        viewModelScope.launch {
            val editor = sharedPreferences.edit()
            editor.putString("token", newToken)
            editor.apply()
            token.value = newToken
        }
    }

    fun getFCMToken(): String? {
        return sharedPreferences.getString("FCMtoken", null)
    }
    fun saveFCMToken(newToken: String) {
        viewModelScope.launch {
            val editor = sharedPreferences.edit()
            editor.putString("FCMtoken", newToken)
            editor.apply()
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            val editor = sharedPreferences.edit()
            editor.remove("token")
            editor.apply()
            token.value = null
        }
    }

    class Factory(private val sharedPreferences: SharedPreferences) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TokenModel(sharedPreferences) as T
        }
    }
}
