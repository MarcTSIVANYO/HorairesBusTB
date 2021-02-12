package fr.istic.mob.horairesbustb.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import fr.istic.mob.horairesbustb.model.StopTime

@Dao
interface StopTimeDao {

    @Query(value = "SELECT * FROM stop_times ORDER BY id ASC")
    fun getAllDataStopTime(): Cursor

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addStopTime(stopTime: StopTime)

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    fun addAllStopTimes(stopTimes: MutableSet<StopTime>)

    @Update
    suspend fun updateStopTime(stopTime: StopTime)

    @Delete
    suspend fun deleteStopTime(stopTime: StopTime)
}