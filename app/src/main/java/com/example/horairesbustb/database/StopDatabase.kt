package com.example.horairesbustb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.horairesbustb.dao.RouteDao
import com.example.horairesbustb.dao.StopDao
import com.example.horairesbustb.model.Route
import com.example.horairesbustb.model.Stop

@Database(entities = [Stop ::class], version = 1, exportSchema = false)
abstract class StopDatabase : RoomDatabase() {

    abstract fun stopDao(): StopDao

    companion object {
        @Volatile
        private var INSTANCE: StopDatabase? = null

        fun getDatabase(context: Context): StopDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StopDatabase::class.java,
                    "stops"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}