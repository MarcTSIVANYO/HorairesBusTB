import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.horairesbustb.model.Trip
import com.example.horairesbustb.repository.TripRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripViewModel(application: Application) : AndroidViewModel(application){
    val getAllDataTrip: LiveData<List<Trip>>
    val tripRepository: TripRepository


    init {
        val tripDao=TripDatabase.getDatabase(application).tripDao()
        tripRepository = TripRepository(tripDao)
        getAllDataTrip = tripRepository.getAllDataTrip
    }

    fun insertTrip(trip: Trip){
        viewModelScope.launch (Dispatchers.IO){
            tripRepository.insertTrip(trip)
        }
    }

    fun updateTrip(trip: Trip){
        viewModelScope.launch (Dispatchers.IO) {
            tripRepository.updateTrip(trip)
        }
    }

    fun deleteTrip(trip: Trip){
        viewModelScope.launch {
            tripRepository.deleteTrip(trip)
        }
    }

}