package hr.sztfr.sztfr_android.util

import hr.sztfr.sztfr_android.data.FirestoreUser
import hr.sztfr.sztfr_android.data.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun handleClick(id: String) {
    if (FirestoreUser.value != null) {
        val favorites = FirestoreUser.value!!.favorites as ArrayList<String>
        favorites.apply {
            if (contains(id)) { remove(id) } else { add(id) }
        }

        GlobalScope.launch {
            UserRepository().updateFavorites(favorites)
        }
    }
}