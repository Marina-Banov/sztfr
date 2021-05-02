package hr.sztfr.sztfr_android.ui.event

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hr.sztfr.sztfr_android.data.model.Event

class EventDetailsViewModel(e: Event, app: Application) : AndroidViewModel(app) {
    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    init {
        _event.value = e
    }
}