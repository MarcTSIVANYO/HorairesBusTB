package com.example.horairesbustb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.horairesbustb.model.Trip

@Dao
interface TripDao {
    @Query(value = "SELECT * FROM trips ORDER BY id ASC")
    fun getAllDataTrip(): LiveData<List<Trip>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrip(trip: Trip)

    @Update
    suspend fun updateTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)
}