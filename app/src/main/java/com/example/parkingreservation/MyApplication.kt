package com.example.parkingreservation

import android.app.Application
import com.example.parkingreservation.dao.CancelReservation
import com.example.parkingreservation.dao.CreateReservation
import com.example.parkingreservation.dao.GetReservations
import com.example.parkingreservation.dao.Login
import com.example.parkingreservation.dao.Signup
import com.example.parkingreservation.repository.CancelReservationRespository
import com.example.parkingreservation.repository.LoginRepository
import com.example.parkingreservation.repository.ReservationRepository
import com.example.parkingreservation.repository.SignupRepository
import com.example.parkingreservation.repository.GetReservationsRespository
import com.google.firebase.FirebaseApp


class MyApplication: Application() {


    val signup by lazy { Signup.createSignup() }
    val signupRepository by lazy { SignupRepository(signup) }

    val login by lazy { Login.createLogin() }
    val loginRepository by lazy { LoginRepository(login) }


}
