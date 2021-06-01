package hr.sztfr.sztfr_android.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.data.repository.EnumsRepository
import hr.sztfr.sztfr_android.data.repository.EventsRepository
import hr.sztfr.sztfr_android.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private var userRepository = UserRepository.getInstance(firestore)
    private var enumsRepository = EnumsRepository.getInstance(firestore)
    private var eventsRepository = EventsRepository.getInstance(firestore)

    init {
        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            // TODO good reason to put splash screen
            userRepository.user.value = userRepository.get()
            enumsRepository.tags.value =
                enumsRepository.get(enumsRepository.TAGS) as ArrayList<String>
            eventsRepository.events.value = eventsRepository.get()
        }
    }
}