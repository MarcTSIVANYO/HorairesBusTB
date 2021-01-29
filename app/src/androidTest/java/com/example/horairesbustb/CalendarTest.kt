package com.example.horairesbustb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.istic.mob.horairesbustb.dao.CalendarDao
import fr.istic.mob.horairesbustb.database.AppDatabase
import fr.istic.mob.horairesbustb.model.Calendar
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CalendarTest {
    private lateinit var calendarDao: CalendarDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        calendarDao = db.calendarDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun writeUserAndReadInList() {
        val calendar: Calendar = Calendar("10", "lundi", "mardi", "mardi", "mardi", "mardi", "mardi", "mardi", "mardi")
        calendarDao.addCalendar(calendar)
        val calendarItem = calendarDao.findByTitle(calendar.monday.toString())
        assertThat(calendarItem, equalTo(calendar))
    }
}