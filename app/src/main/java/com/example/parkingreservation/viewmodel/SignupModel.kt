package com.example.parkingreservation.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parkingreservation.repository.SignupRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupModel (private val signupRepository: SignupRepository): ViewModel() {
    var loading = mutableStateOf(true)
    var error = mutableStateOf(false)
    var success = mutableStateOf(false)

    fun signup(
        fullname: String,
        email: String,
        password: String,
        address: String,
        phonenumber: String,
        onTokenReceived: (String?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = signupRepository.signup(fullname, email, password, address, phonenumber)
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

    class Factory(private val signupRepository: SignupRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignupModel(signupRepository) as T
        }
    }

}