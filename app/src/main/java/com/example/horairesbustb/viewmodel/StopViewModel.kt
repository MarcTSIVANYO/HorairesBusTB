import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.horairesbustb.database.StopDatabase
import com.example.horairesbustb.model.Stop
import com.example.horairesbustb.repository.StopRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StopViewModel(application: Application) : AndroidViewModel(application){
    val getAllDataStop: LiveData<List<Stop>>
    val stopRepository: StopRepository


    init {
        val stopDao=StopDatabase.getDatabase(application).stopDao()
        stopRepository = StopRepository(stopDao)
        getAllDataStop = stopRepository.getAllDataStop
    }

    fun insertStop(stop: Stop){
        viewModelScope.launch (Dispatchers.IO){
            stopRepository.insertStop(stop)
        }
    }

    fun updateStop(stop: Stop){
        viewModelScope.launch (Dispatchers.IO) {
            stopRepository.updateStop(stop)
        }
    }

    fun deleteStop(stop: Stop){
        viewModelScope.launch {
            stopRepository.deleteStop(stop)
        }
    }

}