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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun AdminPage(
    ParcelPage: () -> Unit = {},
    viewModel: ParcelViewModel = viewModel()
) {
    // Observe the LiveData from the viewModel
    val parcels by viewModel.allParcels.observeAsState(initial = emptyList<Parcel>())

    Column(
        modifier = Modifier.fillMaxSize() // The outer Column takes up the full screen
    ) {
        // Inner Column containing the main content
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
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    lineHeight = 40.sp,
                ),
                modifier = Modifier.padding(10.dp)
            )

            AddParcelButton(
                onClick = ParcelPage
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,

            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Text(
                text = "NAME",
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "DATE",
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "IMAGE/CODE",
                modifier = Modifier.weight(1f)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(parcels) { parcel ->
                ParcelRow(parcel)
            }
        }
    }
}

@Composable
fun ParcelRow(parcel: Parcel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = parcel.customerName,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = parcel.date.toString(),
                modifier = Modifier.weight(1.5f)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
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
                Text(text = parcel.trackingNumber)
            }
        }

        Button(
            onClick = {}
        ) {
            Text("Delete")
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

@Preview(showBackground = true)
@Composable
fun AdminPagePreview() {
    KPZParcelTheme {
        AdminPage()
    }
}
