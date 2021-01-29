package fr.istic.mob.start2tb.ViewModel

import android.content.ClipData
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.istic.mob.horairesbustb.model.Stop
import java.util.*

class MyViewModel: ViewModel(){
    val selected = MutableLiveData<Stop>()

    fun select(item: Stop) {
        selected.value = item
    }

    fun getSelected(): LiveData<Stop>? {
        return selected
    }
}