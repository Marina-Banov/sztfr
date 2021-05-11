package hr.sztfr.sztfr_android.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hr.sztfr.sztfr_android.data.model.Event
import java.util.ArrayList

class HomeViewModel(list: ArrayList<Event>, app: Application) : AndroidViewModel(app) {
    private val _events = MutableLiveData<ArrayList<Event>>()
    val events: LiveData<ArrayList<Event>>
        get() = _events

    init {
        _events.value = list
    }
}