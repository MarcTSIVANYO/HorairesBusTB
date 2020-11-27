package com.example.horairesbustb.repository

import androidx.lifecycle.LiveData
import com.example.horairesbustb.dao.RouteDetailDao
import com.example.horairesbustb.model.RouteDetail

class RouteDetailRepository(private val routeDetailDao: RouteDetailDao) {
    val getAllDataRouteDetail: LiveData<List<RouteDetail>> = routeDetailDao.getAllDataRouteDetail()

    suspend fun insertRouteDetail(routeDetail: RouteDetail){
        routeDetailDao.addRouteDetail(routeDetail)
    }

    suspend fun updateRouteDetail(routeDetail: RouteDetail){
        routeDetailDao.updateRouteDetail(routeDetail)
    }

    suspend fun deleteRouteDetail(routeDetail: RouteDetail){
        routeDetailDao.deleteRouteDetail(routeDetail)
    }
}