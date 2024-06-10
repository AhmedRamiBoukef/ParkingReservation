package com.example.parkingreservation.screens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import androidx.annotation.DrawableRes
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.parkingreservation.R
import com.example.parkingreservation.repository.HomeRepository
import com.example.parkingreservation.viewmodel.HomeViewModel
import com.example.parkingreservation.viewmodel.TokenModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun ParkingMap(
    navController: NavHostController,
    currentLocation: Pair<Double, Double>?,
    tokenModel: TokenModel
) {
    val applicationContext = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        if (currentLocation != null) {
            position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                com.google.android.gms.maps.model.LatLng(currentLocation.first, currentLocation.second), 9f
            )
        } else {
            position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                com.google.android.gms.maps.model.LatLng(36.7372, 3.0865), 9f
            )
        }
    }
    val token: String = tokenModel.getToken()!!;
    val homeRepository = HomeRepository(com.example.parkingreservation.dao.Home.createHome(token))
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory(homeRepository))
    val parkings by homeViewModel.parkings
    val parkingSpots = parkings.map { parking ->
        ParkingSpot(
            parking.id,
            parking.address.longitude,
            parking.address.latitude,
            "${parking.pricePerHour}",
            "${parking.address.wilaya} ${parking.address.commune}"
        )
    }

    var selectedSpot by remember { mutableStateOf<ParkingSpot?>(null) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.fillMaxHeight(0.94f)) {
            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = properties,
                ) {

                    parkingSpots.forEach { spot ->
                        MapMarker(
                            context = applicationContext,
                            position = com.google.android.gms.maps.model.LatLng(
                                spot.lat,
                                spot.lng
                            ),
                            title = spot.price,
                            price = spot.price,
                            iconResourceId = R.drawable.icon,
                            onClick = {
                                selectedSpot = spot
                            }
                        )
                    }
                }
                if (selectedSpot != null) {
                    Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                        ParkingSpotDetail(selectedSpot!!, onClose = { selectedSpot = null },navController)
                    }
                }
            }
        }
    }
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun ParkingSpotDetail(spot: ParkingSpot, onClose: () -> Unit, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .clickable(false) {}
            .padding(horizontal = 20.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(text = "Parking Spots", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Box(
                modifier = Modifier
                    .background(Color(0xFFFFF3F3), shape = RoundedCornerShape(15.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(text = spot.price, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = spot.address, fontSize = 18.sp, color = Color(0x612D2D80))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.parking_img),
            contentDescription = "Parking Spot",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            colors = ButtonDefaults.buttonColors(Color(0xFF130F26)),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 10.dp)
                .fillMaxWidth(1.0f),
            onClick = {
                navController.navigate("${Destination.ParkingDetails.route}/${spot.id}")
            }) {
            Text(
                text = "See Details",
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 6.dp)
            )
        }
    }
}

data class ParkingSpot(val id: Int,val lat: Double, val lng: Double, val price: String, val address: String)

@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    title: String,
    price: String,
    @DrawableRes iconResourceId: Int,
    onClick: () -> Unit
) {
    val icon = bitmapDescriptorFromVector(
        context, iconResourceId, price
    )
    Marker(
        state = MarkerState(position = position),
        title = title,
        icon = icon,
        onClick = {
            onClick()
            true
        }
    )
}

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int,
    price: String
): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

    val width = drawable.intrinsicWidth
    val height = drawable.intrinsicHeight + 30
    val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)

    val paint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 36f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textAlign = android.graphics.Paint.Align.CENTER
    }
    canvas.drawText(price, width / 2f, height / 2f - 10f, paint)
    return BitmapDescriptorFactory.fromBitmap(bm)
}
