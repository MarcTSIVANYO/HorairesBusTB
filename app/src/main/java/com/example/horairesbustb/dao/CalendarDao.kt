package com.example.horairesbustb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.horairesbustb.model.Calendar

@Dao
interface CalendarDao {

    @Query(value = "SELECT * FROM calendars ORDER BY id ASC")
    fun getAllDataCalendar(): LiveData<List<Calendar>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCalendar(calendar: Calendar)

    @Update
    suspend fun updateCalendar(calendar: Calendar)

    @Delete
    suspend fun deleteCalendar(calendar: Calendar)
}