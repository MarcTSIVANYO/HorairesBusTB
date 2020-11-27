package com.example.horairesbustb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.horairesbustb.model.Route

@Dao
interface RouteDao {

    @Query(value = "SELECT * FROM routes ORDER BY id ASC")
    fun getAllDataRoute(): LiveData<List<Route>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRoute(route: Route)

    @Update
    suspend fun updateRoute(route: Route)

    @Delete
    suspend fun deleteRoute(route: Route)
}