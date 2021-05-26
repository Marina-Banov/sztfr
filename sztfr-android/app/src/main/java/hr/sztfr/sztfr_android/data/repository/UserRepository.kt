package hr.sztfr.sztfr_android.data.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import hr.sztfr.sztfr_android.data.FirestoreUser
import hr.sztfr.sztfr_android.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository {

    companion object {
        private const val TAG = "UserRepository"
        private const val COLLECTION_NAME = "users"
        private const val FAVORITES = "favorites"
    }

    private val db = FirebaseFirestore.getInstance()
    private val userDocument = db.collection(COLLECTION_NAME)
                                 .document(Firebase.auth.currentUser!!.uid)
    init {
        userDocument.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                FirestoreUser.value = snapshot.toObject(User::class.java)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }

    suspend fun get() = withContext(Dispatchers.IO) {
        try {
            userDocument.get().await().toObject(User::class.java)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            User()
        }
    }

    suspend fun updateFavorites(favorites: List<String>) = withContext(Dispatchers.IO) {
        try {
            val result = userDocument.update(FAVORITES, favorites).await()
            true
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            false
        }
    }
}