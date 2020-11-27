package com.example.horairesbustb.repository

import androidx.lifecycle.LiveData
import com.example.horairesbustb.dao.RouteDao
import com.example.horairesbustb.model.Route

class RouteRepository(private val routeDao: RouteDao) {
    val getAllDataRoute: LiveData<List<Route>> = routeDao.getAllDataRoute()

    suspend fun insertRoute(route: Route){
        routeDao.addRoute(route)
    }

    suspend fun updateRoute(route: Route){
        routeDao.updateRoute(route)
    }

    suspend fun deleteRoute(route: Route){
        routeDao.deleteRoute(route)
    }
}