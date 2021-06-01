package hr.sztfr.sztfr_android.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.repository.EventsRepository
import hr.sztfr.sztfr_android.data.repository.SurveysRepository
import hr.sztfr.sztfr_android.data.repository.SurveysRepository.SurveyFilter
import hr.sztfr.sztfr_android.data.repository.UserRepository
import hr.sztfr.sztfr_android.util.filterByTags
import hr.sztfr.sztfr_android.util.search
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.ArrayList

class FavoritesViewModel: ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val eventsRepository = EventsRepository.getInstance(firestore)
    private val surveysRepository = SurveysRepository()
    private val userRepository = UserRepository.getInstance(firestore)
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _items = MutableLiveData<ArrayList<Filterable>>()
    val items: LiveData<ArrayList<Filterable>>
        get() = _items

    private val _favoriteItems = MutableLiveData<ArrayList<Filterable>>()
    val favoriteItems: LiveData<ArrayList<Filterable>>
        get() = _favoriteItems

    private val _displayItems = MutableLiveData<ArrayList<Filterable>>()
    val displayItems: LiveData<ArrayList<Filterable>>
        get() = _displayItems

    init {
        // TODO quite inefficient
        coroutineScope.launch {
            _items.value = eventsRepository.get() as ArrayList<Filterable>
            _items.value!!.addAll(surveysRepository.get(SurveyFilter.ALL) as ArrayList<Filterable>)
            filterFavorites()
        }
    }

    fun filterFavorites() {
        if (_items.value == null) {
            return
        }
        _favoriteItems.value = ArrayList<Filterable>()
        for (f in userRepository.user.value!!.favorites) {
            _items.value!!.find { it.documentId == f }?.let {
                _favoriteItems.value!!.add(it)
            }
        }
        _displayItems.value = _favoriteItems.value
    }

    fun updateFavorites(tags: ArrayList<String>?) {
        _favoriteItems.value?.let {
            _displayItems.value = filterByTags(it, tags)
        }
    }

    fun updateFavorites(query: String?) {
        _favoriteItems.value?.let {
            _displayItems.value = search(it, query)
        }
    }
}