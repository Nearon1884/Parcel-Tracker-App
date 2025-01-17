package com.example.kpzparcel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.kpzparcel.data.Parcel
import com.example.kpzparcel.data.ParcelRepository
import com.example.kpzparcel.data.ParcelDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParcelViewModel(application: Application) : AndroidViewModel(application) {

    // Initialize the repository
    private val repository: ParcelRepository
    val allParcels: LiveData<List<Parcel>>

    init {
        // Initialize the repository with ParcelDao
        val parcelDao = ParcelDatabase.getDatabase(application).parcelDao()
        repository = ParcelRepository(parcelDao)

        // Get all parcels from the repository
        allParcels = repository.allParcels
    }
    fun getParcelByTrackingNumber(trackingNumber: String): LiveData<Parcel?> {
        return liveData {
            emit(repository.getParcelByTrackingNumber(trackingNumber))
        }
    }
    // Function to add a parcel to the database
    fun addParcel(parcel: Parcel) {
        // Launch a coroutine to add a parcel asynchronously
        viewModelScope.launch {
            repository.addParcel(parcel)
        }
    }

    fun deleteParcel(parcel: Parcel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteParcel(parcel) // Replace with your actual delete logic
        }
    }
}
