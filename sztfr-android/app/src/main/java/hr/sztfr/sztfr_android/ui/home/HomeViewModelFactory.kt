package hr.sztfr.sztfr_android.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hr.sztfr.sztfr_android.data.model.Event

class HomeViewModelFactory(
        private val list: ArrayList<Event>,
        private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(list, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}