package fr.istic.mob.horairesbustb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.istic.mob.horairesbustb.model.Trip


@Dao
interface TripDao {
    @Query(value = "SELECT * FROM trips ORDER BY trip_id ASC")
    fun getAllDataTrip(): LiveData<List<Trip>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addTrip(trip: Trip)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllTrips(trips: MutableSet<Trip>)

    @Update
    suspend fun updateTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)
}