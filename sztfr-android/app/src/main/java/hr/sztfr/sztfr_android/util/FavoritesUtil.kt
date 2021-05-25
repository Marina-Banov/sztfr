package hr.sztfr.sztfr_android.util

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun handleClick(item: Filterable) {
    GlobalScope.launch {
        // TODO I want this to be globally accessible
        //  not to perform this call every time the user clicks <3
        val user = UserRepository().get(Firebase.auth.currentUser!!.uid)
        if (user != null) {
            addFavorite(item)
        }
    }
}

fun addFavorite(item: Filterable) {
    Log.i("FavoritesUtil", "add favorite (" + item.documentId + ")")
}

fun removeFavorite(item: Filterable) {

}