package hr.sztfr.sztfr_android.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.sztfr.sztfr_android.data.model.Filterable
import java.util.ArrayList

class FavoritesViewModel: ViewModel() {
    private val _displayItems = MutableLiveData<ArrayList<Filterable>>()
    val displayItems: LiveData<ArrayList<Filterable>>
        get() = _displayItems

    init {
        _displayItems.value = ArrayList()
    }
}