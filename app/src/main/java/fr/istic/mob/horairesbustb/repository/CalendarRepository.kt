package fr.istic.mob.horairesbustb.repository

import androidx.lifecycle.LiveData
import fr.istic.mob.horairesbustb.dao.CalendarDao
import fr.istic.mob.horairesbustb.model.Calendar

class CalendarRepository(private val calendarDao: CalendarDao) {
    val getAllDataCalendar: LiveData<List<Calendar>> = calendarDao.getAllDataCalendar()

    suspend fun insertCalendar(calendar: Calendar){
        calendarDao.addCalendar(calendar)
    }

    suspend fun updateCalendar(calendar: Calendar){
        calendarDao.updateCalendar(calendar)
    }

    suspend fun deleteCalendar(calendar: Calendar){
        calendarDao.deleteCalendar(calendar)
    }
}