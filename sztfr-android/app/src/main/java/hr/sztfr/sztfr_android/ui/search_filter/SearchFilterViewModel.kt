package hr.sztfr.sztfr_android.ui.search_filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.ArrayList
import hr.sztfr.sztfr_android.data.repository.EnumsRepository
import kotlinx.coroutines.*

class SearchFilterViewModel: ViewModel() {
    private val enumsRepository = EnumsRepository()
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _tags = MutableLiveData<ArrayList<String>>()
    val tags: LiveData<ArrayList<String>>
        get() = _tags

    private val _selectedTags = MutableLiveData<ArrayList<String>>()
    val selectedTags: LiveData<ArrayList<String>>
        get() = _selectedTags

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    init {
        getTags()
        _selectedTags.value = ArrayList()
    }

    private fun getTags() {
        coroutineScope.launch {
            _tags.value = enumsRepository.get(EnumsRepository.TAGS) as ArrayList<String>
        }
    }

    fun updateSelectedTags(tag: String, shouldAdd: Boolean) {
        _selectedTags.value?.apply {
            if (shouldAdd) { add(tag) } else { remove(tag) }
        }
        // Live data is not updated simply by updating the ArrayList
        // Must also update the reference
        _selectedTags.value = _selectedTags.value
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}