package com.example.horairesbustb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.horairesbustb.model.StopTime

@Dao
interface StopTimeDao {

    @Query(value = "SELECT * FROM stop_times ORDER BY id ASC")
    fun getAllDataStopTime(): LiveData<List<StopTime>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStopTime(stopTime: StopTime)

    @Update
    suspend fun updateStopTime(stopTime: StopTime)

    @Delete
    suspend fun deleteStopTime(stopTime: StopTime)
}