package fr.istic.mob.horairesbustb.repository

import androidx.lifecycle.LiveData
import fr.istic.mob.horairesbustb.dao.StopDao
import fr.istic.mob.horairesbustb.model.Stop

class StopRepository(private val stopDao: StopDao) {
    val getAllDataStop: LiveData<List<Stop>> = stopDao.getAllDataStop()

    suspend fun insertStop(stop: Stop){
        stopDao.addStop(stop)
    }

    suspend fun updateStop(stop: Stop){
        stopDao.updateStop(stop)
    }

    suspend fun deleteStop(stop: Stop){
        stopDao.deleteStop(stop)
    }
}