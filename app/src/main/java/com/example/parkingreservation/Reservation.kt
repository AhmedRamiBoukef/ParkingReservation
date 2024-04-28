package com.example.parkingreservation

import TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reservation() {
    val isDatePickerVisible = remember { mutableStateOf(false) }
    val isTimePickerVisible = remember { mutableStateOf(false) }
    val timeState = rememberTimePickerState(0,0,is24Hour = false)
    val nbrHours = remember { mutableIntStateOf(0) }
    val hourPrice = 13.99

    val dateState = rememberDatePickerState()
    val millisToLocalDate = dateState.selectedDateMillis?.let {
        DateUtils().convertMillisToLocalDate(it)
    }
    val dateToString = millisToLocalDate?.let {
        DateUtils().dateToString(millisToLocalDate)
    } ?: ""
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(top = 30.dp)
        .background(Color(0xFFF4F4FA)),// Change the background color here
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally){
        val painter: Painter = painterResource(id = R.drawable.reservation)
        Image(
            painter = painter,
            contentDescription = "reservation image",
            // Other modifiers like contentScale, alignment, etc. can be added here
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround){
            Column {
                Text(text = "ParkingNAme",
                    fontSize = 20.sp, // Change the font size to 20 sp (scaled pixels)
                    fontWeight = FontWeight.Bold, // Change the font weight to bold
                )
                Text(text = "parking adress",
                    color = Color(0x4B2D2D2D), // Hexadecimal color value (e.g., #3366FF)
                    modifier = Modifier.padding(top = 5.dp)

                )
            }
            Box(
                modifier = Modifier
                    .background(color = Color(0xFFFFF3F3),shape = RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = "$${hourPrice}/h",
                    color = Color(0xFFF43939),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold, // Change the font weight to bold
                    modifier = Modifier.padding(vertical = 8.dp , horizontal = 12.dp) // Add padding inside the border
                )
            }
        }
        Text(text = "Reserve Your Place ",
            fontSize = 27.sp, // Change the font size to 20 sp (scaled pixels)
            fontWeight = FontWeight.Bold, // Change the font weight to bold
            modifier = Modifier.padding(top = 8.dp)
        )
        /*DatePicker(
            state = dateState
        )*/
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.White) ,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 15.dp),
            onClick = { isDatePickerVisible.value = true }
        ) {
            Row (modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column {
                    Text(text = "Select Date",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black // Change the opacity to 90%
                        )
                    Text(
                        color = Color.Black.copy(alpha = 0.7f),
                        text = if (dateToString.isNotEmpty()) dateToString else "YYYY-MM-DD"
                    )

                }
                Box(
                    modifier = Modifier
                        .background(color = Color(0xFFFFF3F3),shape = RoundedCornerShape(16.dp))
                ) {
                    val painter: Painter = painterResource(id = R.drawable.calendar)
                    Image(
                        painter = painter,
                        contentDescription = "calendar image",
                        modifier = Modifier.padding(vertical = 8.dp , horizontal = 12.dp) // Add padding inside the border

                        // Other modifiers like contentScale, alignment, etc. can be added here
                    )
                }
            }
        }

        Row (modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ){
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.White) ,
                shape = RoundedCornerShape(10.dp),
                onClick = { isTimePickerVisible.value = true }
            ) {
                Column(modifier = Modifier.padding(bottom = 5.dp),) {
                    Text(text = "Select Time",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black, // Change the opacity to 90%
                    )
                    Text(
                        color = Color.Black.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 5.dp),
                        fontSize = 16.sp,
                        text ="${timeState.hour}:${timeState.minute}"
                    )
                }

            }

            Column(modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(top = 10.dp)
            ) {
                Text(text = "Number of hours ",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black, // Change the opacity to 90%
                )
                Row (modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { nbrHours.value = nbrHours.value-1 },
                        modifier = Modifier
                            .width(50.dp)
                            .height(35.dp),
                        shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 0.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 0.dp
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF43939)) ,

                        ) {
                        Text(text = "-",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold

                            )
                    }
                    Text(text = nbrHours.value.toString(),
                    )
                    Button(onClick = { nbrHours.value = nbrHours.value+1 },
                        modifier = Modifier
                            .width(50.dp)
                            .height(35.dp),
                        shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 8.dp,
                                bottomStart   = 0.dp,
                                bottomEnd = 8.dp
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF43939)) ,

                        ) {
                        Text(text = "+",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
        Row (modifier = Modifier.fillMaxWidth(0.8f)
            .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Total Price : ",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(text = "${nbrHours.value * hourPrice} $",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF43939)

            )

        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black) ,
            modifier = Modifier.padding(horizontal = 10.dp)
                .padding(top = 25.dp)
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Next",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }




        if (isDatePickerVisible.value) {
            DatePickerDialog(
                onDismissRequest = { isDatePickerVisible.value = false },
                confirmButton = {

                    Button(
                        onClick = { isDatePickerVisible.value = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF43939)) ,

                        ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { isDatePickerVisible.value = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF43939)) ,

                        ) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = dateState,
                    showModeToggle = true
                )
            }
        }
        if(isTimePickerVisible.value) {

            TimePickerDialog(
            onCancel = { isTimePickerVisible.value = false },
            onConfirm = { isTimePickerVisible.value = false })
            {
                TimePicker(state = timeState)
            }
        }






    }
}


