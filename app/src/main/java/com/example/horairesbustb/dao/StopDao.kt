package com.example.horairesbustb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.horairesbustb.model.Stop

@Dao
interface StopDao {

    @Query(value = "SELECT * FROM stops ORDER BY id ASC")
    fun getAllDataStop(): LiveData<List<Stop>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStop(stop: Stop)

    @Update
    suspend fun updateStop(stop: Stop)

    @Delete
    suspend fun deleteStop(stop: Stop)
}