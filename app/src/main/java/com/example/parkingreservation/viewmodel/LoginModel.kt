package com.example.parkingreservation.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parkingreservation.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginModel(private val loginRepository: LoginRepository): ViewModel() {
    var loading = mutableStateOf(true)
    var error = mutableStateOf(false)
    var success = mutableStateOf(false)

    fun login(email: String, password: String, onTokenReceived: (String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = loginRepository.login(email, password)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    val receivedToken = response.body()?.token
                    onTokenReceived(receivedToken)
                    success.value = receivedToken != null
                    error.value = receivedToken == null
                } else {
                    error.value = true
                    onTokenReceived(null)
                }
                loading.value = false
            }
        }
    }

    fun sendFCMToken(FCMToken:String){
        CoroutineScope(Dispatchers.IO).launch {
            loginRepository.sendFCMToken(FCMToken)
        }
    }
    var loadingGoogle = mutableStateOf(true)
    var errorGoogle = mutableStateOf(false)
    var successGoogle = mutableStateOf(false)

    fun loginWithGoogle(email: String, fullName: String, googleId:String, onTokenReceived: (String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = loginRepository.loginGoogle(email, fullName, googleId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null) {
                    val receivedToken = response.body()?.token
                    onTokenReceived(receivedToken)
                    successGoogle.value = receivedToken != null
                    errorGoogle.value = receivedToken == null
                } else {
                    errorGoogle.value = true
                    onTokenReceived(null)
                }
                loadingGoogle.value = false
            }
        }
    }

    class Factory(private val loginRepository: LoginRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginModel(loginRepository) as T
        }
    }

}