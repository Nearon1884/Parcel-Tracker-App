package com.example.kpzparcel.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kpzparcel.R
import com.example.kpzparcel.MainActivity
import com.example.kpzparcel.data.Parcel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kpzparcel.ui.theme.KPZParcelTheme
import com.example.kpzparcel.viewmodel.ParcelViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.kpzparcel.ui.theme.AbrilFatFace
import com.example.kpzparcel.ui.theme.BasicRegular
import com.example.kpzparcel.ui.theme.BebasNueue
import com.example.kpzparcel.ui.theme.DotGothic
import com.example.kpzparcel.ui.theme.Montserrat
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AdminPage(
    ParcelPage: () -> Unit = {},
    viewModel: ParcelViewModel = viewModel(),
    navController: NavHostController
) {

    val parcels by viewModel.allParcels.observeAsState(initial = emptyList<Parcel>())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            Text(
                text = "Available Parcel",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    //fontStyle = FontStyle.Italic,
                    lineHeight = 40.sp,
                    color = Color(0xFF6D5E0F)
                ),
                modifier = Modifier.padding(10.dp)
            )
            AddParcelButton2 (
                onClick = ParcelPage
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFECA2))
                .padding(16.dp)
        ) {
            Text(
                text = "NAME",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = "DATE",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = "IMAGE/CODE",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(parcels) { parcel ->
                ParcelRow(parcel, navController)
            }
        }

    }
}

@Composable
fun AddParcelButton2(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
        text = { Text(text = "Add Parcel") },
    )
}

@Composable
fun ParcelRow(parcel: Parcel, navController: NavHostController, viewModel: ParcelViewModel = viewModel()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = parcel.customerName,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Text(
                text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(parcel.date),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                parcel.imageByteArray?.let {
                    val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Parcel Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(100.dp)
                    )
                }
                Text(
                    text = parcel.trackingNumber,
                    textAlign = TextAlign.Center
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { viewModel.deleteParcel(parcel) },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.padding(5.dp)
            ) {
                Text("Delete")
            }

            Button(
                onClick = {
                    navController.navigate("editParcel/${parcel.trackingNumber}")
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.padding(5.dp)
            ) {
                Text("Edit")
            }
        }
    }
}


@Composable
fun AddParcelButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Add Parcel")
    }
}

