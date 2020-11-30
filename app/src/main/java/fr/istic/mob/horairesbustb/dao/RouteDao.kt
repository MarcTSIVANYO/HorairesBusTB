package fr.istic.mob.horairesbustb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.istic.mob.horairesbustb.model.Route

@Dao
interface RouteDao {

    @Query(value = "SELECT * FROM routes ORDER BY route_id ASC")
    fun getAllDataRoute(): LiveData<List<Route>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRoute(route: Route)

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    fun addAllRoutes(routes: MutableSet<Route>)

    @Update
    suspend fun updateRoute(route: Route)

    @Delete
    suspend fun deleteRoute(route: Route)
}