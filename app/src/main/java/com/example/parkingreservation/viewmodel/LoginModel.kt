package com.example.parkingreservation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parkingreservation.repository.LoginRepository
import com.example.parkingreservation.repository.SignupRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginModel(private val loginRepository: LoginRepository): ViewModel() {
    var loading = mutableStateOf(true)
    var error = mutableStateOf(false)
    var success = mutableStateOf(false)

    fun login(email:String, password:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = loginRepository.login(email, password)
            if (response.isSuccessful) {
                success.value = true
            } else {
                error.value = true
            }
            loading.value = false
        }
    }

    class Factory(private val loginRepository: LoginRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginModel(loginRepository) as T
        }
    }

}