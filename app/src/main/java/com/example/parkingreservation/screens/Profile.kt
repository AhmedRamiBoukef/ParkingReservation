import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkingreservation.R
import com.example.parkingreservation.viewmodel.TokenModel

@Composable
fun ProfilePage(tokenModel: TokenModel) {
    val name by remember { mutableStateOf("Rami") }
    val address by remember { mutableStateOf("Address") }
    val phone by remember { mutableStateOf("0555555555") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8FF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Profile Page",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable { /* Handle profile picture click */ }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { /* Do nothing to make it unchangeable */ },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false // Make the text field unchangeable
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { /* Do nothing to make it unchangeable */ },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false // Make the text field unchangeable
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { /* Do nothing to make it unchangeable */ },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false // Make the text field unchangeable
        )

        Spacer(modifier = Modifier.weight(1f)) // Take up the remaining space

        Button(
            onClick = { tokenModel.clearToken() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 60.dp)
        ) {
            Text("Logout", color = Color.White)
        }
    }
}
