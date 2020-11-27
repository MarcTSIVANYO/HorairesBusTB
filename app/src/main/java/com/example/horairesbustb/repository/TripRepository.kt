package com.example.horairesbustb.repository

import androidx.lifecycle.LiveData
import com.example.horairesbustb.dao.TripDao
import com.example.horairesbustb.model.Trip

class TripRepository(private val tripDao: TripDao) {
    val getAllDataTrip: LiveData<List<Trip>> = tripDao.getAllDataTrip()

    suspend fun insertTrip(trip: Trip){
        tripDao.addTrip(trip)
    }

    suspend fun updateTrip(trip: Trip){
        tripDao.updateTrip(trip)
    }

    suspend fun deleteTrip(trip: Trip){
        tripDao.deleteTrip(trip)
    }
}