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

    private val _displayEvents = MutableLiveData<ArrayList<Event>>()
    val displayEvents: LiveData<ArrayList<Event>>
        get() = _displayEvents

    init {
        _events.value = list
        _displayEvents.value = list
    }

    fun filterByTags(tags: ArrayList<String>) {
        if (tags.size == 0) {
            _displayEvents.value = _events.value
            return
        }
        _displayEvents.value = ArrayList()
        for (event in _events.value!!) {
            for (tag in tags) {
                if (event.tags.contains(tag)){
                    _displayEvents.value!!.add(event)
                    break
                }
            }
        }
    }

    fun search(query: String) {
        if (query.isEmpty()) {
            _displayEvents.value = _events.value
            return
        }
        _displayEvents.value = ArrayList()
        for (event in _events.value!!) {
            if (event.title.toLowerCase().contains(query.toLowerCase())) {
                _displayEvents.value!!.add(event)
            }
        }
    }
}