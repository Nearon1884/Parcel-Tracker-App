package com.example.kpzparcel.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap):ByteArray{
        val outputStream =ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
        return outputStream.toByteArray()
    }
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return try {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid ByteArray for Bitmap conversion")
        }
    }

}
