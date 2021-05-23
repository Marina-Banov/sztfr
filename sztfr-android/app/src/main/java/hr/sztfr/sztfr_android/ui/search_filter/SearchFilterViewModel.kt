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

    init { getTags() }

    private fun getTags() {
        coroutineScope.launch {
            _tags.value = enumsRepository.get(EnumsRepository.TAGS) as ArrayList<String>
        }
    }
}