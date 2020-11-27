package com.example.horairesbustb.repository

import androidx.lifecycle.LiveData
import com.example.horairesbustb.dao.StopDao
import com.example.horairesbustb.model.Stop

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