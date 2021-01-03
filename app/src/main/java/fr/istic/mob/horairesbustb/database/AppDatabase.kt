package fr.istic.mob.horairesbustb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.istic.mob.horairesbustb.dao.*
import fr.istic.mob.horairesbustb.model.*


@Database(entities = [Calendar::class, Route::class, Stop::class, StopTime::class, Trip::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun calendarDao(): CalendarDao
    abstract fun routeDao(): RouteDao
    abstract fun stopDao(): StopDao
    abstract fun stopTimeDao(): StopTimeDao
    abstract fun tripDao(): TripDao

}