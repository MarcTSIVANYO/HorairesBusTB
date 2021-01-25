package fr.istic.mob.horairesbustb.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import fr.istic.mob.horairesbustb.model.Stop

@Dao
interface StopDao {

    @Query(value = "SELECT * FROM stops ORDER BY stop_id ASC")
    fun getAllDataStop(): Cursor


    @Query("SELECT DISTINCT s.stop_name FROM stops s, stop_times st, trips t WHERE s.stop_id = st.stop_id AND st.trip_id = t.trip_id AND t.route_id = :routeId and direction_id = :directionId ORDER by CAST(st.stop_sequence AS INTEGER)")
    fun getStops(routeId: String?, directionId: String?): Cursor


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStop(stop: Stop)

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    fun addAllStops(stops: MutableSet<Stop>)

    @Update
    suspend fun updateStop(stop: Stop)

    @Delete
    suspend fun deleteStop(stop: Stop)
}