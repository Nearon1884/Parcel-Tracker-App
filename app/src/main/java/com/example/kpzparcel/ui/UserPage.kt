package com.example.kpzparcel.ui

import android.os.Bundle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
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

@Composable
fun UserPage() {
    val offset2 = Offset(15.0f, 8.0f)
    val image1 = painterResource(R.drawable.parcel)
    val image2 = painterResource(R.drawable.parcel2)
    val image3 = painterResource(R.drawable.parcel3)
    val image4 = painterResource(R.drawable.parcel4)
    val image5 = painterResource(R.drawable.parcel5)
    
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(vertical = 40.dp)
    ) {
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

        Image(
            painter = image2,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp).align(alignment = Alignment.CenterHorizontally)
        )

        Text(
            text = "spx123456789",
            //Placeholder - Tracking Number needs to be based on database

            textAlign = TextAlign.Center,

            style = TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                lineHeight = 40.sp,
            ),
            modifier = Modifier.padding(10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Please collect by 30/10/24 to avoid extra charges",
            //Placeholder - Date needs to be calculated differently

            textAlign = TextAlign.Center,

            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                //fontStyle = FontStyle.Italic,
                lineHeight = 40.sp,
            ),
            modifier = Modifier.padding(20.dp)

        )

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KPZParcelTheme {
        UserPage()
    }
}
