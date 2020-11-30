package fr.istic.mob.horairesbustb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.istic.mob.horairesbustb.model.Stop

@Dao
interface StopDao {

    @Query(value = "SELECT * FROM stops ORDER BY stop_id ASC")
    fun getAllDataStop(): LiveData<List<Stop>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStop(stop: Stop)

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    fun addAllStops(stops: MutableSet<Stop>)

    @Update
    suspend fun updateStop(stop: Stop)

    @Delete
    suspend fun deleteStop(stop: Stop)
}