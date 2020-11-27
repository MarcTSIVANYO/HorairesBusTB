package com.example.horairesbustb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.horairesbustb.model.RouteDetail

@Dao
interface RouteDetailDao {
    @Query(value = "SELECT * FROM route_details ORDER BY id ASC")
    fun getAllDataRouteDetail(): LiveData<List<RouteDetail>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRouteDetail(routeDetails: RouteDetail)

    @Update
    suspend fun updateRouteDetail(routeDetails: RouteDetail)

    @Delete
    suspend fun deleteRouteDetail(routeDetail: RouteDetail)
}