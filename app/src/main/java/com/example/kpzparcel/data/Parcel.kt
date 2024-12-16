package com.example.kpzparcel.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "parcel_table")
data class Parcel(
    @PrimaryKey val trackingNumber: String,
    val customerName: String,
    val date: Date,
    val imageUrl: String
)
