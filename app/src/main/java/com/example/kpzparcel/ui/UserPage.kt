package com.example.kpzparcel.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kpzparcel.R
import com.example.kpzparcel.ui.theme.KPZParcelTheme
import com.example.kpzparcel.viewmodel.ParcelViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun UserPage(trackingNumber: String,viewModel: ParcelViewModel = viewModel()) {
    val offset2 = Offset(15.0f, 8.0f)
    Log.d("UserPage", "Current tracking number: $trackingNumber")
    val image1 = painterResource(R.drawable.parcel)
    val image2 = painterResource(R.drawable.parcel2)
    val image3 = painterResource(R.drawable.parcel3)
    val image4 = painterResource(R.drawable.parcel4)
    val image5 = painterResource(R.drawable.parcel5)

    // Sample tracking number, replace with actual dynamic tracking number


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(vertical = 40.dp)
    ) {


        ParcelDetails(trackingNumber = trackingNumber, viewModel = viewModel)




        Spacer(modifier = Modifier.height(10.dp))





    }
}

@Composable
fun ParcelDetails(trackingNumber: String, viewModel: ParcelViewModel = viewModel()) {

    val parcel by viewModel.getParcelByTrackingNumber(trackingNumber).observeAsState()

    parcel?.let {
        Column (modifier = Modifier.padding(10.dp)){
            Text(
                text = "Your parcel is ready to be collected!",
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

            Spacer(modifier = Modifier.height(10.dp))

            Text("Customer Name: ${it.customerName}")
            Text(
                text = "Parcel Date: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it.date)}",

            )

            Text("Tracking Number: ${it.trackingNumber}")
            it.imageByteArray?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Parcel Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(200.dp).align(alignment = Alignment.CenterHorizontally)
                )
            }
            Text(
                text = trackingNumber,
                textAlign = TextAlign.Center,

                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    lineHeight = 40.sp,
                ),
                modifier = Modifier
                    .fillMaxWidth() // Makes the Text occupy the full width
                    .padding(10.dp)
            )
            val calendar = Calendar.getInstance().apply {
                time = it.date
                add(Calendar.DAY_OF_YEAR, 3)
            }
            val collectByDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)

            Text(
                text = "Please collect by $collectByDate to avoid extra charges",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 40.sp,
                ),
                modifier = Modifier.padding(20.dp)
            )
        }
    } ?: run {
        Text(
            text = "Parcel not found",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                lineHeight = 40.sp,
            ),
            modifier = Modifier.padding(10.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KPZParcelTheme {
        UserPage(trackingNumber = "abc123")
    }
}
