import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.horairesbustb.database.CalendarDatabase
import com.example.horairesbustb.model.Calendar
import com.example.horairesbustb.repository.CalendarRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel(application: Application) : AndroidViewModel(application){
    val getAllDataCalendar: LiveData<List<Calendar>>
    val calendarRepository: CalendarRepository


    init {
        val calendarDao=CalendarDatabase.getDatabase(application).calendarDao()
        calendarRepository = CalendarRepository(calendarDao)
        getAllDataCalendar = calendarRepository.getAllDataCalendar
    }

    fun insertCalendar(calendar: Calendar){
        viewModelScope.launch (Dispatchers.IO){
            calendarRepository.insertCalendar(calendar)
        }
    }

    fun updateCalendar(calendar: Calendar){
        viewModelScope.launch (Dispatchers.IO) {
            calendarRepository.updateCalendar(calendar)
        }
    }

    fun deleteCalendar(calendar: Calendar){
        viewModelScope.launch {
            calendarRepository.deleteCalendar(calendar)
        }
    }

}