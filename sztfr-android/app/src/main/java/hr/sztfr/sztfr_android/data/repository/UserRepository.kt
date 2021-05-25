package hr.sztfr.sztfr_android.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository {

    companion object {
        private const val COLLECTION_NAME = "users"
    }

    private val db = FirebaseFirestore.getInstance()
    private val userDocument = db.collection(COLLECTION_NAME)
                                 .document(Firebase.auth.currentUser!!.uid)

    suspend fun get() = withContext(Dispatchers.IO) {
        try {
            val user = userDocument.get().await().toObject(User::class.java)
            val favorites = userDocument.collection("favorites")
                                        .get()
                                        .await()
            for (f in favorites) {
                user!!.favorites.add(f.toObject(SurveyModel::class.java))
            }
            user
        } catch (e: Exception) {
            User()
        }
    }

    suspend fun addFavorite(item: Filterable) = withContext(Dispatchers.IO) {
        try {
            val result = userDocument.collection("favorites")
                                     .document(item.documentId)
                                     .set(item)
                                     .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}

object FirestoreUser {
    var value: User? = null
}