package hr.sztfr.sztfr_android.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.data.model.Enums
import hr.sztfr.sztfr_android.data.model.Tags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.ArrayList

class EnumsRepository {

    companion object {
        private const val TAG = "EnumsRepository"
        private const val COLLECTION_NAME = "enums"
        const val TAGS = "tags"

        fun getClass(enumType: String): Class<out Enums> = when(enumType) {
            TAGS -> Tags::class.java
            else -> Enums::class.java
        }
    }

    private val db = FirebaseFirestore.getInstance()
    private val enumsCollection = db.collection(COLLECTION_NAME)

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