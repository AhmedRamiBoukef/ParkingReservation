package com.example.parkingreservation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parkingreservation.repository.SignupRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupModel (private val signupRepository: SignupRepository): ViewModel() {
    var loading = mutableStateOf(true)
    var error = mutableStateOf(false)
    var success = mutableStateOf(false)

    fun signup(fullname:String, email:String, password:String, address:String, phonenumber:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = signupRepository.signup(fullname,email, password, address, phonenumber)
            if (response.isSuccessful) {
                success.value = true
            } else {
                error.value = true
            }
            loading.value = false
        }
    }

    class Factory(private val signupRepository: SignupRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignupModel(signupRepository) as T
        }
    }

}