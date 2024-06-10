package com.example.parkingreservation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.parkingreservation.viewmodel.TokenModel

@Composable
fun Profile(tokenModel: TokenModel) {
    Column {
        Button(onClick = { tokenModel.clearToken() }) {
            Text("Logout")
        }
    }

}