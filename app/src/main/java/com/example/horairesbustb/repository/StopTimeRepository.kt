package com.example.horairesbustb.repository

import androidx.lifecycle.LiveData
import com.example.horairesbustb.dao.StopTimeDao
import com.example.horairesbustb.model.StopTime

class StopTimeRepository(private val stopTimeDao: StopTimeDao) {
    val getAllDataStopTime: LiveData<List<StopTime>> = stopTimeDao.getAllDataStopTime()

    suspend fun insertStopTime(stopTime: StopTime){
        stopTimeDao.addStopTime(stopTime)
    }

    suspend fun updateStopTime(stopTime: StopTime){
        stopTimeDao.updateStopTime(stopTime)
    }

    suspend fun deleteStopTime(stopTime: StopTime){
        stopTimeDao.deleteStopTime(stopTime)
    }
}