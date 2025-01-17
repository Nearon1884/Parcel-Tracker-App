package com.example.kpzparcel.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ParcelDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addParcel(parcel: Parcel)

    @Query("SELECT * FROM parcel_table ORDER BY customerName ASC")
    fun allParcels(): LiveData<List<Parcel>>  // Renamed method

    @Query("SELECT * FROM parcel_table WHERE trackingNumber = :trackingNumber")
    suspend fun getParcelByTrackingNumber(trackingNumber: String): Parcel?

    @Delete
    suspend fun delete(parcel: Parcel)


}
