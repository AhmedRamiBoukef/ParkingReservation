package com.example.parkingreservation

import android.app.Application
import com.example.parkingreservation.dao.CreateReservation
import com.example.parkingreservation.dao.Login
import com.example.parkingreservation.dao.Signup
import com.example.parkingreservation.repository.LoginRepository
import com.example.parkingreservation.repository.ReservationRepository
import com.example.parkingreservation.repository.SignupRepository


class MyApplication: Application() {
    val signup by lazy { Signup.createSignup() }
    val signupRepository by lazy { SignupRepository(signup) }
    val reservation by lazy {CreateReservation.getInstance()}
    val reservationRepository by lazy {  ReservationRepository(reservation)}
    val login by lazy { Login.createLogin() }
    val loginRepository by lazy { LoginRepository(login) }
}
