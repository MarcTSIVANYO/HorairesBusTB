package com.example.horairesbustb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.horairesbustb.dao.*
import com.example.horairesbustb.model.*

@Database(entities = [Calendar ::class, Route::class, Stop ::class, StopTime ::class, Trip ::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun calendarDao(): CalendarDao
    abstract fun routeDao(): RouteDao
    abstract fun stopDao(): StopDao
    abstract fun stopTimeDao(): StopTimeDao
    abstract fun tripDao(): TripDao
}