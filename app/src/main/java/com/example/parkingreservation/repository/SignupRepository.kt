package com.example.parkingreservation.repository

import com.example.parkingreservation.dao.Signup
import com.example.parkingreservation.data.entities.SignupRequest

class SignupRepository(private val signup: Signup) {
    suspend fun signup(fullname:String, email:String, password:String, address:String, phonenumber:String) = signup.signup(
        SignupRequest(fullName=fullname,email=email,password=password,phone=phonenumber,address=address)
    )
}