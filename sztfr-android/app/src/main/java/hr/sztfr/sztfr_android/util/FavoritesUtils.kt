package hr.sztfr.sztfr_android.util

import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.data.repository.UserRepository
import kotlinx.coroutines.*

fun handleClick(id: String) {
    val userRepository = UserRepository.getInstance(FirebaseFirestore.getInstance())

    if (userRepository.user.value!!.uid != "") {
        val favorites = userRepository.user.value!!.favorites
        favorites.apply {
            if (contains(id)) { remove(id) } else { add(id) }
        }

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            userRepository.updateFavorites(favorites)
        }
    } else {
        // TODO
    }
}