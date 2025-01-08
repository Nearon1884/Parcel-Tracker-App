package com.example.kpzparcel.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "parcel_table")
data class Parcel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerName: String,
    val trackingNumber: String,
    val date: Date,
    val imageByteArray: ByteArray // Store image as a byte array
)