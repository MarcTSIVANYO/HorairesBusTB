package fr.istic.mob.horairesbustb.repository

import androidx.lifecycle.LiveData
import fr.istic.mob.horairesbustb.dao.TripDao
import fr.istic.mob.horairesbustb.model.Trip

class TripRepository(private val tripDao: TripDao) {
    val getAllDataTrip: LiveData<List<Trip>> = tripDao.getAllDataTrip()

    suspend fun insertTrip(trip: Trip){
        tripDao.addTrip(trip)
    }

    suspend fun insertAllTrip(trips: MutableSet<Trip>){
        tripDao.addAllTrips(trips)
    }

    suspend fun updateTrip(trip: Trip){
        tripDao.updateTrip(trip)
    }

    suspend fun deleteTrip(trip: Trip){
        tripDao.deleteTrip(trip)
    }
}