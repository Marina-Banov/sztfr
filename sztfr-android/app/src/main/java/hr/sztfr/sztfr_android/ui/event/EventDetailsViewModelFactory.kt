package hr.sztfr.sztfr_android.ui.event

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hr.sztfr.sztfr_android.data.model.Event

class EventDetailsViewModelFactory(
    private val event: Event,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventDetailsViewModel::class.java)) {
            return EventDetailsViewModel(event, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}