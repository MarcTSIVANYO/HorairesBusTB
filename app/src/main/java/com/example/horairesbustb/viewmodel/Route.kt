import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.horairesbustb.database.RouteDatabase
import com.example.horairesbustb.model.Route
import com.example.horairesbustb.repository.RouteRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteViewModel(application: Application) : AndroidViewModel(application){
    val getAllDataRoute: LiveData<List<Route>>
    val routeRepository: RouteRepository

    init {
        val routeDao=RouteDatabase.getDatabase(application).routeDao()
        routeRepository = RouteRepository(routeDao)
        getAllDataRoute = routeRepository.getAllDataRoute
    }

    fun insertRoute(route: Route){
        viewModelScope.launch (Dispatchers.IO){
            routeRepository.insertRoute(route)
        }
    }

    fun updateRoute(route: Route){
        viewModelScope.launch (Dispatchers.IO) {
            routeRepository.updateRoute(route)
        }
    }

    fun deleteRoute(route: Route){
        viewModelScope.launch {
            routeRepository.deleteRoute(route)
        }
    }

}