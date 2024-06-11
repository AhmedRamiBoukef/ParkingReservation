package com.example.parkingreservation.screens

import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavHostController
import com.example.parkingreservation.R
import com.example.parkingreservation.viewmodel.LoginModel
import com.example.parkingreservation.viewmodel.TokenModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID

@Composable
fun LandingPage(navController: NavHostController, loginModel: LoginModel, tokenModel: TokenModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)


        val rawNounce = UUID.randomUUID().toString()
        val bytes = rawNounce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNounce = digest.fold(""){str, it -> str + "%02x".format(it)}

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("92446058481-vhk2ufjmmsmeo72ma7jis9j4vgb901ak.apps.googleusercontent.com")
            .setAutoSelectEnabled(true)
            .setNonce(hashedNounce)
        .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                val credential = result.credential

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                Log.d("Log", googleIdTokenCredential.profilePictureUri.toString())
                googleIdTokenCredential.displayName?.let { Log.d("Log", it) }
                googleIdTokenCredential.data.getString("com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID")
                    ?.let { Log.d("Log", it) }
                googleIdTokenCredential.familyName?.let { Log.d("Log", it) }
                googleIdTokenCredential.givenName?.let { Log.d("Log", it) }
                val email: String = googleIdTokenCredential.data.getString("com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID") ?: ""
                val familyName: String = googleIdTokenCredential.familyName ?: ""
                val givenName: String = googleIdTokenCredential.givenName ?: ""

                loginModel.loginWithGoogle(email, "$familyName $givenName", googleIdToken) { token ->
                    if (token != null) {
                        tokenModel.saveToken(token)
                        if (tokenModel.getFCMToken() == null){
                            CoroutineScope(Dispatchers.IO).launch {
                                val token = Firebase.messaging.token.await()
                                Log.d("djamel token", "fcm Token : ${token}")
                                loginModel.sendFCMToken(token)
                                withContext(Dispatchers.Main) {
                                    tokenModel.saveFCMToken(token)
                                }
                            }
                        }
                        navController.navigate(Destination.Home.route) {
                            popUpTo(Destination.Landing.route) { inclusive = true }
                        }
                        Toast.makeText(context, "You are signed in!", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("td", "response.isSuccessful.toString()")
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }


            } catch (e: GetCredentialException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: GoogleIdTokenParsingException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }



    }


    Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom, modifier = Modifier
        .fillMaxSize()
        .background(
            Color(0xFFEAEAF3)
        )){
        Image(
            painter = painterResource(id = R.drawable.landing1),
            contentDescription = "landing1",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth(1.0F)
                .padding(horizontal = 40.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Welcome", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF2D2D2D))
        Text(text = "Find a best possible way to park", fontSize = 14.sp, color = Color(0xF02D2D2D))
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            colors = ButtonDefaults.buttonColors(Color(0xFF2D2D2D)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 50.dp, vertical = 10.dp)
                .fillMaxWidth(1.0f),
            onClick = { navController.navigate(Destination.Login.route) }) {
            Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.message),
                    contentDescription = "mail",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
                Text(
                    text = "Login with email",
                    color = Color.White,
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .padding(start = 20.dp)
                )
            }
        }
        Button(
            colors = ButtonDefaults.buttonColors(Color.White),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 50.dp, vertical = 10.dp)
                .fillMaxWidth(1.0f),
            onClick = onClick
        ) {
            Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "mail",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
                Text(
                    text = "Login with Google",
                    color = Color(0xFF2D2D2D),
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .padding(start = 20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(text = "Don't have an account?  ", fontSize = 12.sp, color = Color(0xFF2D2D2D))
            Text(text = "Sign Up", fontSize = 12.sp, color = Color(0xFFF43939), modifier = Modifier.clickable {
                navController.navigate(Destination.Signup.route)
            })
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}



