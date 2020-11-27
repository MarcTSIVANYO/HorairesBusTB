package com.example.horairesbustb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.horairesbustb.dao.RouteDetailDao
import com.example.horairesbustb.model.RouteDetail

@Database(entities = [RouteDetail ::class], version = 1, exportSchema = false)
abstract class RouteDetailDatabase : RoomDatabase() {

    abstract fun routeDetailDao(): RouteDetailDao

    companion object {
        @Volatile
        private var INSTANCE: RouteDetailDatabase? = null

        fun getDatabase(context: Context): RouteDetailDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RouteDetailDatabase::class.java,
                    "RouteDetails"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}