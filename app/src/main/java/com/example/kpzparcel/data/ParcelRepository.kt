package com.example.kpzparcel.data

import androidx.lifecycle.LiveData

class ParcelRepository(private val parcelDao: ParcelDao) {


    val allParcels: LiveData<List<Parcel>> = parcelDao.allParcels()


    suspend fun addParcel(parcel: Parcel) {
        parcelDao.addParcel(parcel)
    }
}
