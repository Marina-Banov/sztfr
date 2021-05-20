package hr.sztfr.sztfr_android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.sztfr.sztfr_android.data.FirestoreRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import hr.sztfr.sztfr_android.data.model.Tags

class MainViewModel: ViewModel() {
    var firestoreRepository = FirestoreRepository()
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _tags = MutableLiveData<List<String>>()
    val tags: LiveData<List<String>>
        get() = _tags

    init {
        coroutineScope.launch {
            _tags.value = try {
                withContext(Dispatchers.IO) {
                    firestoreRepository.getTags().get().await().toObject(Tags::class.java)?.values
                }
            } catch (e: Exception) { ArrayList() }
        }
    }
}