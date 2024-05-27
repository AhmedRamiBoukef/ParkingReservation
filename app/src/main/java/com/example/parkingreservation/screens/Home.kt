package com.example.parkingreservation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.parkingreservation.R
import com.example.parkingreservation.data.entities.Parking
import com.example.parkingreservation.repository.HomeRepository
import com.example.parkingreservation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


@Composable
fun Home(navController: NavHostController) {
    val homeRepository = HomeRepository(com.example.parkingreservation.dao.Home.createHome())
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory(homeRepository))
    val parkings = homeViewModel.parkings.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF081024))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                HeaderSection()
            }
            item {
                ParkingSpacesSection(navController, homeViewModel, parkings)
            }
        }
    }
}
/*
fun getDummyParkings(): List<Parking> {
    return listOf(
        Parking(
            id = 1,
            photo = "url_de_la_photo_1",
            nom = "Parking 1",
            address = "Adresse 1",
            description = "Description du parking 1",
            nbrTotalPlaces = 50,
            nbrDisponiblePlaces = 20,
            places = "Emplacements du parking 1"
        ),
        Parking(
            id = 2,
            photo = "url_de_la_photo_2",
            nom = "Parking 2",
            address = "Adresse 2",
            description = "Description du parking 2",
            nbrTotalPlaces = 70,
            nbrDisponiblePlaces = 30,
            places = "Emplacements du parking 2"
        ),
        Parking(
            id = 3,
            photo = "url_de_la_photo_3",
            nom = "Parking 3",
            address = "Adresse 3",
            description = "Description du parking 3",
            nbrTotalPlaces = 80,
            nbrDisponiblePlaces = 40,
            places = "Emplacements du parking 3"
        ),
        Parking(
            id = 4,
            photo = "url_de_la_photo_4",
            nom = "Parking 4",
            address = "Adresse 4",
            description = "Description du parking 4",
            nbrTotalPlaces = 60,
            nbrDisponiblePlaces = 25,
            places = "Emplacements du parking 4"
        ),
        Parking(
            id = 5,
            photo = "url_de_la_photo_5",
            nom = "Parking 5",
            address = "Adresse 5",
            description = "Description du parking 5",
            nbrTotalPlaces = 90,
            nbrDisponiblePlaces = 50,
            places = "Emplacements du parking 5"
        )
    )
}*/

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Column {
                    Row {
                        Text(
                            text = "Hello Djamel \uD83D\uDC4B\uD83C\uDFFB",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(start = 16.dp),
                        )
                    }
                    Text(
                        text = "Find an easy parking spot",
                        color = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(55.dp))
                Icon(
                    painter = painterResource(id = R.drawable.notification_icon),
                    contentDescription = "Notification Icon",
                    tint = Color.White.copy(alpha = 0.4f),
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = Color(0XFF2A344E), shape = RoundedCornerShape(10.dp))
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                val searchText = remember { mutableStateOf("") }
                OutlinedTextField(
                    value = searchText.value,
                    onValueChange = { searchText.value = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 10.dp)
                        .background(Color(0XFF2A344E), shape = RoundedCornerShape(10.dp)),
                    placeholder = { Text(text = "Search", color = Color.White.copy(alpha = 0.4f)) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = "Search Icon",
                            tint = Color.White.copy(alpha = 0.4f),
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        Color.White.copy(alpha = 0.6f),
                        unfocusedBorderColor = Color.Transparent
                    ),
                )
            }
        }
    }
}

@Composable
fun ParkingSpacesSection(navController: NavHostController, homeViewModel: HomeViewModel, parkings: List<Parking>) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Nearest", "Popular", "Wanted")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF4F4FA),
                shape = AbsoluteRoundedCornerShape(topLeft = 40.dp, topRight = 40.dp)
            )
            .padding(horizontal = 30.dp)
            .padding(vertical = 20.dp),
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            indicator = {},
            containerColor = Color.Transparent,
            divider = {},
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        selectedTab = index
                        when (index) {
                            0 -> homeViewModel.fetchNearestParkings(35.55, 6.5)
                            1 -> homeViewModel.fetchPopularParkings()
                            2 -> homeViewModel.fetchWantedParkings()
                        }
                    },
                    modifier = Modifier.background(Color.Transparent),
                    text = {
                        if (selectedTab == index) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.home_menu_bg),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.FillBounds
                                )
                                Text(
                                    text = tab,
                                    color = if (selectedTab == index) Color.White else Color.Gray,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        } else {
                            Text(
                                text = tab,
                                color = if (selectedTab == index) Color.White else Color.Gray,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            )
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            parkings.take(5).forEach { parking ->
                ParkingItem(parking, navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ParkingItem(parking: Parking, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
            .clickable { navController.navigate("parkingDetails/${parking.id}") }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            /*Image(
                painter = rememberAsyncImagePainter(model = parking.photo),
                contentDescription = "Parking Icon",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )*/
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.fillMaxSize()) {
                Row {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = parking.nom,
                        color = Color(0xFF2D2D2D),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "7 min",
                        color = Color(0xFFF43939).copy(alpha = 0.8f),
                        modifier = Modifier
                            .background(
                                color = Color(0xFFFFF3F3),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Text(
                    text = "${parking.address.commune}, ${parking.address.wilaya}",
                    color = Color(0xFF2D2D2D)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row {
                    Text(
                        text = "$${parking.pricePerHour}",
                        color = Color(0xFFF43939),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                    )
                    Text(
                        text = "/hour",
                        color = Color(0xFFF43939),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }
}

