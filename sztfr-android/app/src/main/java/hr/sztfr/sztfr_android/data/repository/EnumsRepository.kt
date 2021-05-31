package hr.sztfr.sztfr_android.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.data.model.Enums
import hr.sztfr.sztfr_android.data.model.Tags
import hr.sztfr.sztfr_android.util.SingletonHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.ArrayList

class EnumsRepository private constructor(db: FirebaseFirestore) {

    private val TAG = "EnumsRepository"
    private val COLLECTION_NAME = "enums"
    val TAGS = "tags"

    companion object : SingletonHolder<EnumsRepository, FirebaseFirestore>(::EnumsRepository)

    private val enumsCollection = db.collection(COLLECTION_NAME)
    val tags = MutableLiveData<ArrayList<String>>()

    private fun getClass(enumType: String): Class<out Enums> = when(enumType) {
        TAGS -> Tags::class.java
        else -> Enums::class.java
    }

    suspend fun get(enumType: String) = withContext(Dispatchers.IO) {
        try {
            enumsCollection.document(enumType)
                .get()
                .await()
                .toObject(getClass(enumType))?.values
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            ArrayList<Any>()
        }
    }

}