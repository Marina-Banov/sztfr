package hr.sztfr.sztfr_android.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository {

    companion object {
        private const val COLLECTION_NAME = "users"
    }

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection(COLLECTION_NAME)

    suspend fun get(id: String) = withContext(Dispatchers.IO) {
        try {
            usersCollection.document(id)
                .get()
                .await()
                .toObject(User::class.java)
        } catch (e: Exception) {
            User()
        }
    }

}