package fr.istic.mob.horairesbustb.repository

import androidx.lifecycle.LiveData
import fr.istic.mob.horairesbustb.dao.RouteDao
import fr.istic.mob.horairesbustb.model.Route

class RouteRepository(private val routeDao: RouteDao) {
    val getAllDataRoute: LiveData<List<Route>> = routeDao.getAllDataRoute()

    suspend fun insertRoute(route: Route){
        routeDao.addRoute(route)
    }

    suspend fun insertAllTrip(routes: MutableSet<Route>){
        routeDao.addAllRoutes(routes)
    }

    suspend fun updateRoute(route: Route){
        routeDao.updateRoute(route)
    }

    suspend fun deleteRoute(route: Route){
        routeDao.deleteRoute(route)
    }
}