package hr.sztfr.sztfr_android.data.repository

import android.util.Log
import hr.sztfr.sztfr_android.data.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventsRepository {
    companion object {
        private const val TAG = "EventsRepository"
    }

    suspend fun get() = withContext(Dispatchers.IO) {
        try {
            val list = ArrayList<Event>()
            for (i in 1..5) {
                list.add(Event(
                    i.toString(),
                    "dummy",
                    "RITEH cool event $i",
                    "Vrijeme",
                    "Lokacija",
                    "Organizator",
                    listOf("Tag"),
                    "Lorem ipsum bitch"
                ))
            }
            list
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            ArrayList()
        }
    }
}