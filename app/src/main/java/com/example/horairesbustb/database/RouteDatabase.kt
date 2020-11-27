package com.example.horairesbustb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.horairesbustb.dao.RouteDao
import com.example.horairesbustb.model.Route

@Database(entities = [Route ::class], version = 1, exportSchema = false)
abstract class RouteDatabase : RoomDatabase() {

    abstract fun routeDao(): RouteDao

    companion object {
        @Volatile
        private var INSTANCE: RouteDatabase? = null

        fun getDatabase(context: Context): RouteDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RouteDatabase::class.java,
                    "routes"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
