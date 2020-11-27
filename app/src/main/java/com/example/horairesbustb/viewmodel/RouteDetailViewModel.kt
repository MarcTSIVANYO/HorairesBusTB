import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.horairesbustb.database.RouteDetailDatabase
import com.example.horairesbustb.model.RouteDetail
import com.example.horairesbustb.repository.RouteDetailRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteDetailViewModel(application: Application) : AndroidViewModel(application){
    val getAllDataRouteDetail: LiveData<List<RouteDetail>>
    val routeDetailRepository: RouteDetailRepository


    init {
        val routeDetailDao=RouteDetailDatabase.getDatabase(application).routeDetailDao()
        routeDetailRepository = RouteDetailRepository(routeDetailDao)
        getAllDataRouteDetail = routeDetailRepository.getAllDataRouteDetail
    }

    fun insertRouteDetail(routeDetail: RouteDetail){
        viewModelScope.launch (Dispatchers.IO){
            routeDetailRepository.insertRouteDetail(routeDetail)
        }
    }

    fun updateRouteDetail(routeDetail: RouteDetail){
        viewModelScope.launch (Dispatchers.IO) {
            routeDetailRepository.updateRouteDetail(routeDetail)
        }
    }

    fun deleteRouteDetail(routeDetail: RouteDetail){
        viewModelScope.launch {
            routeDetailRepository.deleteRouteDetail(routeDetail)
        }
    }

}