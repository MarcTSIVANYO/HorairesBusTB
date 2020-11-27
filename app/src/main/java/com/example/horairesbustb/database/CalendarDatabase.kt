package com.example.horairesbustb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.horairesbustb.dao.CalendarDao
import com.example.horairesbustb.model.Calendar

@Database(entities = [Calendar ::class], version = 1, exportSchema = false)
abstract class CalendarDatabase : RoomDatabase() {

    abstract fun calendarDao(): CalendarDao

    companion object {
        @Volatile
        private var INSTANCE: CalendarDatabase? = null

        fun getDatabase(context: Context): CalendarDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalendarDatabase::class.java,
                    "Calendars"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
