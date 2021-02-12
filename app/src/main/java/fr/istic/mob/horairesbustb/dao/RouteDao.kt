package fr.istic.mob.horairesbustb.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import fr.istic.mob.horairesbustb.model.Route

@Dao
interface RouteDao {

    @Query(value = "SELECT * FROM routes ORDER BY route_id ASC")
    fun getAllDataRoute(): Cursor

    @Query(value = "SELECT * FROM routes WHERE route_long_name LIKE :name OR route_short_name LIKE :name ")
    fun getLikeRoute(name : String?): Cursor

    @Query(value = "SELECT * FROM routes WHERE route_id =:route_id")
    fun getDirectionRoute(route_id:String?): Cursor

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRoute(route: Route)

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    fun addAllRoutes(routes: MutableSet<Route>)

    @Update
    suspend fun updateRoute(route: Route)

    @Delete
    suspend fun deleteRoute(route: Route)
}