package com.example.kpzparcel.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Parcel::class], version = 1)
@TypeConverters(Converters::class)
abstract class ParcelDatabase : RoomDatabase() {
    abstract fun parcelDao(): ParcelDao

    companion object {
        @Volatile
        private var INSTANCE: ParcelDatabase? = null

        fun getDatabase(context: Context): ParcelDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParcelDatabase::class.java,
                    "parcel_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
