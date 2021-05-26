package hr.sztfr.sztfr_android.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.util.filterByTags
import hr.sztfr.sztfr_android.util.search
import java.util.ArrayList

class HomeViewModel(list: ArrayList<Event>, app: Application) : AndroidViewModel(app) {

    private val _events = MutableLiveData<ArrayList<Event>>()
    private val _displayEvents = MutableLiveData<ArrayList<Event>>()
    val displayEvents: LiveData<ArrayList<Event>>
        get() = _displayEvents

    init {
        _events.value = list
        _displayEvents.value = list
    }

    fun updateEvents(tags: ArrayList<String>) {
        _displayEvents.value = filterByTags(_events.value!!, tags)
    }

    fun updateEvents(query: String) {
        _displayEvents.value = search(_events.value!!, query)
    }
}