package hr.sztfr.sztfr_android.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.util.SingletonHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class EventsRepository private constructor(db: FirebaseFirestore) {

    private val TAG = "EventsRepository"
    private val COLLECTION_NAME = "events"

    companion object : SingletonHolder<EventsRepository, FirebaseFirestore>(::EventsRepository)

    private val eventsCollection = db.collection(COLLECTION_NAME)
    val events = MutableLiveData<ArrayList<Event>>()

    init {
        events.value = ArrayList()
    }

    suspend fun get() = withContext(Dispatchers.IO) {
        try {
            val querySnapshot = eventsCollection.get().await()
            val result = ArrayList<Event>()
            for (event in querySnapshot) {
                result.add(event.toObject(Event::class.java))
            }
            result
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            ArrayList()
        }
    }
}