package fr.istic.mob.horairesbustb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.istic.mob.horairesbustb.model.Calendar

@Dao
interface CalendarDao {

    @Query(value = "SELECT * FROM calendars ORDER BY service_id ASC")
    fun getAllDataCalendar(): LiveData<List<Calendar>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCalendar(calendar: Calendar)

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    fun addAllCalendars(calendars: MutableSet<Calendar>)

    @Update
    suspend fun updateCalendar(calendar: Calendar)

    @Delete
    suspend fun deleteCalendar(calendar: Calendar)


    @Query("SELECT * FROM calendars WHERE monday LIKE :title")
    fun findByTitle(title: String): Calendar
}